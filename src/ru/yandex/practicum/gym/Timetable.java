package ru.yandex.practicum.gym;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Timetable {

    private Map<DayOfWeek, List<TrainingSession>> sessions = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        sessions.putIfAbsent(day, new ArrayList<>());
        List<TrainingSession> daySessions = sessions.get(day);

        int index = Collections.binarySearch(daySessions, trainingSession, Comparator.comparing(TrainingSession::getTimeOfDay));
        if (index < 0) {
            index = -(index + 1);
        }
        daySessions.add(index, trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return sessions.getOrDefault(dayOfWeek, new ArrayList<>());
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        List<TrainingSession> daySessions = sessions.get(dayOfWeek);
        if (daySessions == null) {
            return new ArrayList<>();
        }

        TrainingSession searchKey = new TrainingSession(null, null, null, timeOfDay);
        int index = Collections.binarySearch(daySessions, searchKey, Comparator.comparing(TrainingSession::getTimeOfDay));

        if (index < 0) {
            return new ArrayList<>();
        }

        int start = index;
        while (start > 0 && daySessions.get(start - 1).getTimeOfDay().equals(timeOfDay)) {
            start--;
        }

        int end = index;
        while (end < daySessions.size() - 1 && daySessions.get(end + 1).getTimeOfDay().equals(timeOfDay)) {
            end++;
        }

        return new ArrayList<>(daySessions.subList(start, end + 1));
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Long> counts = new HashMap<>();
        for (List<TrainingSession> daySessions : sessions.values()) {
            for (TrainingSession session : daySessions) {
                counts.merge(session.getCoach(), 1L, Long::sum);
            }
        }

        List<CounterOfTrainings> result = new ArrayList<>();
        for (Map.Entry<Coach, Long> entry : counts.entrySet()) {
            result.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }
        Collections.sort(result);
        return result;
    }
}
