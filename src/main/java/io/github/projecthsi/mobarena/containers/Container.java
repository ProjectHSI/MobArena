package io.github.projecthsi.mobarena.containers;

import io.github.projecthsi.mobarena.arena.Arena;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Container<I, V> {
    private HashMap<I, V> tracked = new HashMap<>();

    private ReadWriteLock trackedLock = new ReentrantReadWriteLock();
    private Lock readLock = trackedLock.readLock();
    private Lock writeLock = trackedLock.writeLock();

    /**
     * @deprecated Usage of this method is not recommended due to thread safety reasons. Use the indirect methods.
     */
    @Deprecated
    public void setTrackedMobs(HashMap<I, V> newTrackedMobs) {
        tracked = newTrackedMobs;
    }

    public void addTrackedMob(I index, V value) {
        try {
            writeLock.lock();
            tracked.put(index, value);
        } finally {
            writeLock.unlock();
        }
    }

    public void removeTracked(I index) {
        try {
            writeLock.lock();
            tracked.remove(index);
        } finally {
            writeLock.unlock();
        }
    }

    public V getTracked(I index) {
        try {
            readLock.lock();
            return tracked.get(index);
        } finally {
            readLock.unlock();
        }
    }

    public boolean containsTracked(I index) {
        try {
            readLock.lock();
            return tracked.containsKey(index);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * @deprecated Usage of this method is not recommended due to thread safety reasons. Use the indirect methods.
     */
    @Deprecated
    public HashMap<I, V> getTracked() {
        return tracked;
    }

    public static class Containers {
        public  static Container<String, Arena> arenaContainer = new Container<>();
        public static Container<Mob, Arena> mobContainer = new Container<>();
        public static Container<Player, Arena> playerContainer = new Container<>();
    }
}
