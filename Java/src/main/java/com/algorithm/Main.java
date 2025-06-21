package com.algorithm;

import com.algorithm.multithreading.TaskScheduler;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        testTaskScheduler();
    }

    public static void testTaskScheduler(){
        TaskScheduler scheduler = new TaskScheduler();
        int[] delays = {8000, 5000, 2000, 2500, 6000, 3000, 2000, 1000, 500};
        
        for(int i = 0; i < delays.length; i++){
            int id = i;
            try{
                Thread.sleep(10);
                long scheduleTime = System.currentTimeMillis() + delays[id];
                Runnable callback = () -> System.out.println("Task id " + id + " delay + " + delays[id] + " schedule time " + scheduleTime);
                scheduler.scheduleCallback(callback, scheduleTime);
            }catch(Exception exception){
                System.out.println(exception.getMessage());
            }
        }
    }
}