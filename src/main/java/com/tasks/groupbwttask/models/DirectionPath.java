package com.tasks.groupbwttask.models;

import com.tasks.groupbwttask.enumerations.Direction;

public class DirectionPath implements Comparable<DirectionPath> {
    private Direction direction;
    private int priority;
    
    public DirectionPath(Direction direction, int priority) {
		this.direction = direction;
		this.priority = priority;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	@Override
	public int compareTo(DirectionPath o) {
		return o.getPriority()-priority;
	}
	    
}
