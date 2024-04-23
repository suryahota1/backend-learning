package com.example.multithread1;
import java.util.Map;
import java.util.concurrent.*;


class SmapleReentrantLock {
	private boolean isLocked = false;
	private int lockCount = 0;
	Thread lockedBy;
	
	void lock () {
		Thread currentThread = Thread.currentThread();
		while ( isLocked && lockedBy != currentThread ) {
//			wait();
		}
		isLocked = true;
		lockCount++;
		lockedBy = currentThread;
	}
	
	void unlock () {
		if ( Thread.currentThread() == lockedBy ) {
			if ( --lockCount == 0 ) {
				isLocked = false;
//				notify();
			}
		}
	}
}

class SampleReadWriteLock {
	
	private int readCount = 0;
	private boolean writeExists = false;
	private int writeRequests = 0;
	
	void readLock () {
		while ( writeExists || writeRequests > 0 ) {
//			wait();
		}
		readCount++;
	}
	
	void writeLock() {
		writeRequests++;
		while ( writeExists || readCount > 0 ) {
//			wait();
		}
		writeExists = true;
		writeRequests--;
	}
	
	void unlockRead () {
		readCount--;
//		notifyAll();
	}
	
	void unlockWrite () {
		writeExists = false;
//		notifyAll();
	}
}

class SampleReentrantReadWriteLock {
	
	private Map<Long, Integer> readMap;
	private boolean writeExists = false;
	private int writeRequests = 0;
	private Long writingThreadId;
	
	private boolean isThreadWriting ( long threadId ) {
		return writeExists && writingThreadId == threadId;
	}
	
	private boolean isThreadReading ( long threadId ) {
		return readMap.get(threadId) != null;
	}
	
	private boolean areThereOtherReadingThreads ( long threadId ) {
		return readMap.get(threadId) == null && readMap.size() > 0;
	}
	
	boolean canGrantReadAccess ( long threadId ) {
		if ( isThreadWriting(threadId) ) return true;
		if ( isThreadReading(threadId) ) {
			if ( writeRequests > 0 ) return false;
			return true;
		}
		if ( writeExists || writeRequests > 0 ) return false;
		return true;
	}
	
	boolean canGrantWriteAccess ( long threadId ) {
		if ( isThreadWriting(threadId) ) return true;
		if ( isThreadReading(threadId) ) return false;
		if ( writeExists || writeRequests > 0 ) return false;
		if ( areThereOtherReadingThreads(threadId) ) return false;
		return true;
	}
	
	void readLock () {
		long currThreadId = Thread.currentThread().getId();
		while ( !canGrantReadAccess(currThreadId) ) {
//			wait();
		}
		readMap.put(currThreadId, readMap.getOrDefault(currThreadId, 0) + 1);
	}
	
	void unlockRead () {
		long currThreadId = Thread.currentThread().getId();
		boolean hasCurrThreadReadAccess = isThreadReading(currThreadId);
		if ( !hasCurrThreadReadAccess ) return;
		int count = readMap.get(currThreadId);
		if ( count == 1 ) readMap.remove(currThreadId);
		else readMap.put(currThreadId, count - 1);
//		notifyAll();
	}
	
	void writeLock() {
		writeRequests++;
		long currThreadId = Thread.currentThread().getId();
		while ( !canGrantWriteAccess(currThreadId) ) {
//			wait();
		}
		writingThreadId = currThreadId;
		writeRequests--;
		writeExists = true;
	}
	
	void unlockWrite () {
		long currThreadId = Thread.currentThread().getId();
		if ( !isThreadWriting(currThreadId) ) return;
		writeExists = false;
		writingThreadId = null;
//		notifyAll();
	}
}

public class ThreadDemo1 {
	
}
