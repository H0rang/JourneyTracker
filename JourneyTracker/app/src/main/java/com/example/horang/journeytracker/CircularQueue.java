package com.example.horang.journeytracker;

import android.location.Location;

/**
 * Created by horang on 23/04/16.
 */
public class CircularQueue{
    private int size;
    private int head;
    private int tail;
    private Location[] queue;

    CircularQueue(int n){
        queue = new Location[n];
        size = 0;
        head = 0;
        tail = 0;
    }

    public Location[] getQueue() {
        return queue;
    }

    public int getHead() {
        return head;
    }

    public int getTail() {
        return tail;
    }

    public void addLocation(Location loc){
        if(size == 0){
            queue[0] = loc;
            size++;
        }
        else{
            if(size < queue.length){
                size++;
            }
            else{
                head = (head + 1) % queue.length;
            }
            tail = (tail + 1) % queue.length;
            queue[tail] = loc;
        }
    }

    public void deleteQueue(){
        size = 0;
        head = 0;
        tail = 0;
    }

    public float getSpeed(){
        float speed = queue[tail].getSpeed();
        return speed * 3.6f;
    }

    public float getAverageSpeed(){
        float speed = 0;
        for(int i = 0; i < size; i++)
            speed += queue[(head + i) % 30].getSpeed() * 3.6f;
        speed = speed / (float)size;
        return speed;
    }
}
