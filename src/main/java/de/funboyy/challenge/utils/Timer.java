package de.funboyy.challenge.utils;

import lombok.Getter;
import lombok.Setter;

public class Timer {

    private long start = 0;
    private long duration = 0;
    private boolean running = false;
    @Getter @Setter private boolean finished = false;

    public void start() {
        if (this.running || this.finished) {
            return;
        }

        this.running = true;
        this.start = System.currentTimeMillis();
    }

    public void stop() {
        if (!this.running) {
            return;
        }

        this.duration += (System.currentTimeMillis() - this.start);
        this.start = 0;
        this.running = false;
    }

    public long getDuration() {
        if (this.start == 0) {
            return this.duration;
        }

        return (System.currentTimeMillis() - this.start) + this.duration;
    }

    public boolean isRunning() {
        return this.running && !this.finished;
    }

    @Override
    public String toString() {
        final long duration = getDuration();

        final long days = duration / 1000 / 60 / 60 / 24;
        final long hours = duration / 1000 / 60 / 60 % 24;
        final long minutes = duration / 1000 / 60 % 60;
        final long seconds = duration / 1000 % 60;

        final long[] times = new long[]{ days, hours, minutes, seconds };
        final String[] units = new String[]{ "d", "h", "m", "s" };

        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < times.length; i++) {
            if (builder.length() != 0) {
                builder.append(" ");
            }

            if (times[i] <= 0 && builder.length() == 0 && i != times.length - 1) {
                continue;
            }

            builder.append(times[i]).append(units[i]);
        }

        return builder.toString();
    }
}
