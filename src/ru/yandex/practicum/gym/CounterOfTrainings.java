package ru.yandex.practicum.gym;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {
    private final Coach coach;
    private final long count;

    public CounterOfTrainings(Coach coach, long count) {
        this.coach = coach;
        this.count = count;
    }

    public Coach getCoach() {
        return coach;
    }

    public long getCount() {
        return count;
    }

    @Override
    public int compareTo(CounterOfTrainings o) {
        // Sort by count descending
        return Long.compare(o.count, this.count);
    }

    @Override
    public String toString() {
        return coach.getName() + " " + coach.getSurname() + ": " + count;
    }
}
