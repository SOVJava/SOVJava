package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Olympiad{
    private String fileName;
    private Map<Integer, ArrayList<Pupil>> pupilsLevel;
    private Map<Integer, ArrayList<Pupil>> pupilsSchool;

    public Olympiad(String fileName) throws IOException {
        this.fileName = fileName;
        this.load();
    }

    private void load() throws IOException {
        pupilsLevel = new HashMap<>();
        pupilsSchool = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String firstLine = br.readLine();
            String[] arrFirstLine = firstLine.split(";");
            while (br.ready()) {
                try {
                    String pupilLine = br.readLine();
                    String[] pupil = pupilLine.split(";");
                    if (arrFirstLine[2].equals("level")) {
                        if (!pupilsLevel.containsKey(Integer.valueOf(pupil[2])))
                            pupilsLevel.put(Integer.valueOf(pupil[2]), new ArrayList<Pupil>());
                        pupilsLevel.get(Integer.valueOf(pupil[2])).add(new Pupil(pupil[0], pupil[1],
                                Integer.parseInt(pupil[2]), Integer.parseInt(pupil[3])));
                    }
                    else if (arrFirstLine[2].equals("school")){
                        if (!pupilsSchool.containsKey(Integer.valueOf(pupil[2])))
                            pupilsSchool.put(Integer.valueOf(pupil[2]), new ArrayList<Pupil>());
                        pupilsSchool.get(Integer.valueOf(pupil[2])).add(new Pupil(pupil[0], pupil[1],
                                Integer.parseInt(pupil[2]), Integer.parseInt(pupil[3]), 0));
                    }
                } catch (IOException | NumberFormatException ignored) { }
            }
        }
    }

    public Map<Integer, ArrayList<Pupil>> winnersByLevel() {
        Map<Integer, Integer> maxPoint = new HashMap<>();
        for (Map.Entry<Integer, ArrayList<Pupil>> para : pupilsLevel.entrySet()) {
            int maxP = Integer.MIN_VALUE;
            for (int i = 0; i < para.getValue().size(); i++) {
                if (maxP < para.getValue().get(i).getPoint())
                    maxP = para.getValue().get(i).getPoint();
            }
            maxPoint.put(para.getKey(), maxP);
        }
        Map<Integer, ArrayList<Pupil>> win = new HashMap<>();
        for (Map.Entry<Integer, Integer> item : maxPoint.entrySet()) {
            ArrayList<Pupil> pupils = this.pupilsLevel.get(item.getKey());
            for (int i = 0; i < pupils.size(); i++) {
                if (item.getValue().equals(pupils.get(i).getPoint())){
                    if (!win.containsKey(item.getKey()))
                        win.put(item.getKey(), new ArrayList<Pupil>());
                    win.get(item.getKey()).add(pupils.get(i));
                }
            }
        }
        return win;
    }

    // средний бал
    public Map<Integer, Double> averagePointByLevel() {
        Map<Integer, Double> res = new HashMap<>();
        for (Map.Entry<Integer, ArrayList<Pupil>> para : pupilsLevel.entrySet()) {
            Integer level = para.getKey();
            int allPoint = 0;
            for (Pupil pupil : para.getValue()) {
                allPoint += pupil.getPoint();
            }
            res.put(level, (double) allPoint / para.getValue().size());
        }
        return res;
    }

    public ArrayList<Integer> sortAverage(){
        ArrayList<Map.Entry<Integer, Double>> sortList = new ArrayList<>(averagePointByLevel().entrySet());
        sortList.sort(new Comparator<Map.Entry<Integer, Double>>() {
            @Override
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                if (o1.getValue().equals(o2.getValue()))
                    return Integer.compare(o1.getKey(), o2.getKey());
                return Double.compare(o1.getValue(), o2.getValue());
            }
        });
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < sortList.size(); i++) {
            res.add(sortList.get(i).getKey());
        }
        return res;
    }

    private static ArrayList<Pupil> winner(ArrayList<Pupil> pupils) {
        ArrayList<Pupil> result = new ArrayList<>();
        int maxPoint = 0;
        for (Pupil pupil : pupils) {
            if (maxPoint < pupil.getPoint())
                maxPoint = pupil.getPoint();
        }
        for (Pupil pupil : pupils) {
            if (pupil.getPoint() == maxPoint)
                result.add(pupil);
        }
        return result;
    }

    //Колличество победителей по классам
    public Map<Integer, Integer> countWinnersByLevel() {
        Map<Integer, Integer> result = new HashMap<>();
        for (Map.Entry<Integer, ArrayList<Pupil>> para : pupilsLevel.entrySet()) {
            result.put(para.getKey(), winner(para.getValue()).size());
        }
        return result;
    }

    //Абсолютные чемпионы не зависимо от класса
    public ArrayList<Pupil> absoluteWinnersByLevel() {
        ArrayList<Pupil> result = new ArrayList<>();
        int maxPoint = 0;
        for (Map.Entry<Integer, ArrayList<Pupil>> para : pupilsLevel.entrySet()) {
            for (Pupil pupil : para.getValue()) {
                if (pupil.getPoint() > maxPoint)
                    maxPoint = pupil.getPoint();
            }
        }
        for (Map.Entry<Integer, ArrayList<Pupil>> para : pupilsLevel.entrySet()) {
            for (Pupil pupil : para.getValue()) {
                if (pupil.getPoint() == maxPoint)
                    result.add(pupil);
            }
        }
        return result;
    }

    //Количество участников по школам
    //Выведите номера этих школ в порядке возрастания.
    public Map<Integer, Integer> countPupilsBySchool(){
        Map<Integer,Integer> result = new TreeMap<>();
        for (Map.Entry<Integer, ArrayList<Pupil>> para :pupilsSchool.entrySet()) {
            result.put(para.getKey(), para.getValue().size());
        }
        return result;
    }

    //Максимальный балл в каждом классе
    public Map<Integer, Integer> maxPointByLevel(){
        Map<Integer, Integer> result = new HashMap<>();
        for (Map.Entry<Integer, ArrayList<Pupil>> para : pupilsLevel.entrySet()) {
            result.put(para.getKey(), winner(para.getValue()).get(0).getPoint());
        }
        return result;
    }

    //Колличество баллов второго места в каждом классе
    public Map<Integer, Integer> secondPlaceByLevel(){
        Map<Integer, Integer> res = new HashMap<>();
        Map<Integer, Integer> win = maxPointByLevel();
        for (Map.Entry<Integer, ArrayList<Pupil>> para : pupilsLevel.entrySet()) {
            Pupil[] levl = para.getValue().toArray(new Pupil[0]);
            Arrays.sort(levl, new Comparator<Pupil>() {
                @Override
                public int compare(Pupil o1, Pupil o2) {
                    return Integer.compare(o2.getPoint(), o1.getPoint());
                }
            });
            int w = win.get(para.getKey());
            for (int i = 0; i < levl.length; i++) {
                if (w > levl[i].getPoint()){
                    res.put(para.getKey(), levl[i].getPoint());
                    break;
                }
                /*if (i == levl.length-1){
                    res.put(para.getKey(), null);
                }*/
            }
        }
        return res;
    }

    //Призёры
    public ArrayList<Pupil> prizewinner(){
        if (pupilsLevel.isEmpty())
            return null;
        /*ArrayList<Pupil> result = new ArrayList<>();
        int maxPoint = absoluteWinnersByLevel().get(0).getPoint();
        while (true) {
            for (Map.Entry<Integer, ArrayList<Pupil>> para : pupilsLevel.entrySet()) {
                for (int i = 0; i < para.getValue().size(); i++) {
                    if (maxPoint - 1 == para.getValue().get(i).getPoint())
                        result.add(para.getValue().get(i));
                }
            }
            if (!result.isEmpty()) {
                return result;
            } else {
                maxPoint--;
            }
        }*/
        return null;
    }

    //Определите школы, из которых в олимпиаде принимало участие больше всего участников.
    //Выведите номера этих школ в порядке возрастания.
    public Map<Integer, Integer> maxCountPupilBySchool(){
        Map<Integer, Integer> result = new HashMap<>();
        Map<Integer, Integer> countPupil = countPupilsBySchool();
        int max = Integer.MIN_VALUE;
        for (Map.Entry<Integer, Integer> para : countPupil.entrySet()) {
            if (para.getValue()>max)
                max = para.getValue();
        }
        for (Map.Entry<Integer, Integer> para : countPupil.entrySet()) {
            if (max == para.getValue())
                result.put(para.getKey(), para.getValue());
        }
        return result;
    }

    //Определите школы, из которых в олимпиаде принимало участие меньше всего участников.
    //Выведите номера этих школ в порядке возрастания.
    public ArrayList<Integer> minCountPupilBySchool(){
        ArrayList<Integer> result = new ArrayList<>();
        Map<Integer, Integer> countPupil = countPupilsBySchool();
        int min = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Integer> para : countPupil.entrySet()) {
            if (para.getValue()<min)
                min = para.getValue();
        }
        for (Map.Entry<Integer, Integer> para : countPupil.entrySet()) {
            if (min == para.getValue())
                result.add(para.getKey());
        }
        return result;
    }


    public Pupil[] allPupil(){
        ArrayList<Pupil> res = new ArrayList<>();
        if (pupilsSchool.isEmpty()) {
            for (Map.Entry<Integer, ArrayList<Pupil>> para : pupilsLevel.entrySet()) {
                res.addAll(para.getValue());
            }
        } else {
            for (Map.Entry<Integer, ArrayList<Pupil>> para : pupilsSchool.entrySet()) {
                res.addAll(para.getValue());
            }
        }
        return res.toArray(new Pupil[0]);
    }

    public ArrayList<Integer> numSchool(){
        Map<Integer, ArrayList<Pupil>> school = pupilsSchool;
        ArrayList<Map.Entry<Integer, ArrayList<Pupil>>> sortSchool = new ArrayList<>(pupilsSchool.entrySet());
        sortSchool.sort(new Comparator<Map.Entry<Integer, ArrayList<Pupil>>>() {
            @Override
            public int compare(Map.Entry<Integer, ArrayList<Pupil>> o1, Map.Entry<Integer, ArrayList<Pupil>> o2) {
                if (o1.getValue().size() == o2.getValue().size())
                    return Integer.compare(o1.getKey(), o2.getKey());
                return Integer.compare(o2.getValue().size(), o1.getValue().size());
            }
        });
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < sortSchool.size(); i++) {
            res.add(sortSchool.get(i).getKey());
        }
        return res;
    }


    /*public static void write(ArrayList<Pupil> arr, String fileNameOut) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileNameOut))) {
            bw.write("surname;name;level;point");
            for (Pupil pupil : arr) {
                bw.write(pupil.getSurname() + ";" + pupil.getName() + ";" + pupil.getLevel() + ";" +
                        pupil.getPoint());
            }
        }
    }*/

    @Override
    public String toString() {
        return "Olympiad{" +
                "pupilsLevel=" + pupilsLevel +
                ", pupilsSchool=" + pupilsSchool +
                '}';
    }
}
