
package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, mondaySessions.size());
        assertEquals(singleTrainingSession, mondaySessions.get(0));

        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertTrue(tuesdaySessions.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, mondaySessions.size());

        List<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(2, thursdaySessions.size());
        assertEquals(thursdayChildTrainingSession, thursdaySessions.get(0)); // 13:00
        assertEquals(thursdayAdultTrainingSession, thursdaySessions.get(1)); // 20:00

        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertTrue(tuesdaySessions.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        assertEquals(1, sessions.size());
        assertEquals(singleTrainingSession, sessions.get(0));

        List<TrainingSession> emptySessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        assertTrue(emptySessions.isEmpty());
    }

    @Test
    void testCountByCoachesDescending() {
        Timetable timetable = new Timetable();
        Coach coach1 = new Coach("Иванов", "И.", "И.");
        Coach coach2 = new Coach("Петров", "П.", "П.");
        Group group = new Group("G", Age.ADULT, 60);

        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.MONDAY, new TimeOfDay(12, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.MONDAY, new TimeOfDay(14, 0)));

        List<CounterOfTrainings> stats = timetable.getCountByCoaches();
        assertEquals(2, stats.size());
        assertEquals(coach1, stats.get(0).getCoach());
        assertEquals(2, stats.get(0).getCount());
        assertEquals(coach2, stats.get(1).getCoach());
        assertEquals(1, stats.get(1).getCount());
    }
    
    @Test
    void testGetTrainingSessionsForDayAndTimeMultiple() {
       Timetable timetable = new Timetable();
       Group group = new Group("G", Age.ADULT, 60);
       Coach coach = new Coach("C", "N", "M");
       TimeOfDay time = new TimeOfDay(10, 0);
       
       TrainingSession s1 = new TrainingSession(group, coach, DayOfWeek.MONDAY, time);
       TrainingSession s2 = new TrainingSession(group, coach, DayOfWeek.MONDAY, time);
       
       timetable.addNewTrainingSession(s1);
       timetable.addNewTrainingSession(s2);
       
       List<TrainingSession> res = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, time);
       assertEquals(2, res.size());
    }
    
    @Test
    void testGetTrainingSessionsForDayEmpty() {
        Timetable timetable = new Timetable();
        List<TrainingSession> res = timetable.getTrainingSessionsForDay(DayOfWeek.FRIDAY);
        assertNotNull(res);
        assertTrue(res.isEmpty());
    }
}
