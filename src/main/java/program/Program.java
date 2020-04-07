package program;

import model.Olympiad;
import model.Pupil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Program {
    public static void main(String[] args) {

        try {
            Olympiad olympiad = new Olympiad("data1.csv");
            System.out.println(olympiad);

            Map<Integer, ArrayList<Pupil>> winners = olympiad.winnersByLevel();
            System.out.println(winners);

            Map<Integer, Double> averagePoint = olympiad.averagePointByLevel();
            System.out.println(averagePoint);

            ArrayList<Integer> sortAverage = olympiad.sortAverage();
            System.out.println(sortAverage);

            Map<Integer, Integer> countWinners = olympiad.countWinnersByLevel();
            System.out.println(countWinners);

            ArrayList<Pupil> absoluteWinners = olympiad.absoluteWinnersByLevel();
            System.out.println(absoluteWinners);

            Map<Integer, Integer> maxPointByLevel = olympiad.maxPointByLevel();
            System.out.println(maxPointByLevel);

            Map<Integer, Integer> secondPlaceByLevel = olympiad.secondPlaceByLevel();
            System.out.println(secondPlaceByLevel);

            ArrayList<Pupil> prizewinner = olympiad.prizewinner();
            System.out.println(prizewinner);

            Map<Integer, Integer> countPupils = olympiad.countPupilsBySchool();
            System.out.println(countPupils);

            Map<Integer, Integer> maxCountPupilBySchool = olympiad.maxCountPupilBySchool();
            System.out.println(maxCountPupilBySchool);

            ArrayList<Integer> minCountPupilBySchool = olympiad.minCountPupilBySchool();
            System.out.println(minCountPupilBySchool);

            ArrayList<Integer> sortSchool = olympiad.numSchool();
            System.out.println(sortSchool);

            Map<Integer, ArrayList<Pupil>> r = olympiad.winSchools();
            System.out.println(r);

            ArrayList<Integer> sortedWinSchools = olympiad.sortedWinSchools();
            System.out.println(sortedWinSchools + "sortedWinSchools");

            /*Pupil[] allPupil = olympiad.allPupil();
            for (Pupil pupil:allPupil) {
                System.out.println(pupil);
            }
            Arrays.sort(allPupil);
            System.out.println();
            for (Pupil pupil:allPupil) {
                System.out.println(pupil);
            }*/

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
