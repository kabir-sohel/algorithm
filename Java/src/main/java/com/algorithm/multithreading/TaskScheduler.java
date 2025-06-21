package com.algorithm.multithreading;

import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/*
 * Problem : 

You are given a few api

API that is responsible to calculate a callback function after relative_time_stamp_in_mills 
from the time of the call setCallBackHandler.
void setCallBackHandler(int relative_time_stamp_in_mills);

API that gives current system time in mills.
int getSysTime();

You have to write a method called ScheduleCallback(call_back_func_pointer, time_in_mills) 
that does the job of scheduling a call to call_back_func_pointer after time_in_mills from the call.

The calls might come in parallel.
setCallBackHandler can only save ONE TRIGGER AT A TIME. So if the new request is supposed to get executed first, you need to handle that gracefully.
setCallBackHandler takes in relative time.
 */

/*
 * Approach
 * class Task : contains callback, schedule time
 * create a custom callback (callbackHandler) that is called is stored and called by the native scheduler
 * maintain a priority queue on schedule time
 * each time a new task comes in, put it in the queue,
 * if a earlier to schedule task comes in, reschedule
 * Reschedule means : 
 *  1. update earliestScheduledTime 
 *  2. call the setCallbackHandler again
 */

// Mimicking native 
abstract class SerialTaskScheduler {
    private Runnable callback;
    private long delay;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> future;
    protected SerialTaskScheduler(){
        delay = Integer.MAX_VALUE;
        scheduler = Executors.newSingleThreadScheduledExecutor();
    }
    protected void initializeCallback(Runnable callback){
        this.callback = callback;
    }
    abstract void scheduleCallback(Runnable callback, long timeInMillis);
    protected void setCallBackHandler(long delay){
        if(this.callback == null){
            // Need to initialize callback
        }
        this.delay = delay;
        //cancel if there is already a callback scheduled which is not cancelled
        if(future != null && !future.isCancelled()){
            future.cancel(false);
        }

        //delay schedule
        future = scheduler.schedule(this.callback, this.delay, TimeUnit.MILLISECONDS);
    }

    protected long getCurrentTimeInMillis(){
        return System.currentTimeMillis();
    }
}


public class TaskScheduler extends SerialTaskScheduler{
  
    private PriorityQueue<Task> priorityQueue;
    private long latestScheduledTime;
    private Object lock;
    private class Task {
        long scheduleTime;
        Runnable callback;
        Task(Runnable callback, long scheduleTime){
            this.callback = callback;
            this.scheduleTime = scheduleTime;
        }
    }


    public TaskScheduler(){
        this.lock = new Object();
        this.priorityQueue = new PriorityQueue<>((a, b) -> Long.compare(a.scheduleTime, b.scheduleTime));
        this.latestScheduledTime = Long.MAX_VALUE;
        initializeCallback(this::callbackHandler);
    }

    public void scheduleCallback(Runnable callback, long timeInMillis){
        synchronized(lock){
            priorityQueue.offer(new Task(callback, timeInMillis));
            if(timeInMillis < this.latestScheduledTime){
                reschedule();
            }
        }
        
    }

    private void reschedule(){
        if(priorityQueue.isEmpty())return;
        Task task = priorityQueue.peek();
        if(this.latestScheduledTime > task.scheduleTime){
            long currentTime = getCurrentTimeInMillis();
            long delay = Math.max(0, task.scheduleTime - currentTime);
            setCallBackHandler(delay);
            this.latestScheduledTime = task.scheduleTime;
        }
    }

    protected void callbackHandler(){
        //execute all tasks in parallel which has schedule time <= now()
        while(!priorityQueue.isEmpty()){
            Task task = priorityQueue.peek();
            long currentTime = getCurrentTimeInMillis();
            if(task.scheduleTime <= currentTime){
                new Thread(task.callback).start(); // run in a new thread
                priorityQueue.poll(); //remove this task.
            }
            else break; 
        }
        //reset latest schedule time.
        this.latestScheduledTime = Long.MAX_VALUE;
        if(!priorityQueue.isEmpty()){
            reschedule(); // we need to reschedule the next task
        }
    } 
}
