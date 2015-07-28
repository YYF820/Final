package ua.nure.hanzha.SummaryTask4.jsouparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ua.nure.hanzha.SummaryTask4.db.util.PasswordHash;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneratorSql {

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://www.vstup.info/2014/92/i2014i92p177779.html");
        Map<Integer, List<String>> entrants = new HashMap<>();
        Document doc = Jsoup.parse(url, 3000);

        Element table = doc.select("table[class=striped]").first();
        Element tBody = table.select("tbody").first();
        Elements tr = tBody.select("tr");

        for (Element elementTr : tr) {
            Elements td = elementTr.select("td");
            int id = Integer.parseInt(td.get(0).text());
            String[] FIOarr = td.get(1).text().replaceAll("\'", "\'\'").split(" ");
            List<String> FIO = new ArrayList<>(3);
            Collections.addAll(FIO, FIOarr);
            try {
                FIO.get(2);
            } catch (IndexOutOfBoundsException e) {
                FIO.add("Хесусовіч");
            }
            List<String> personInfo = new ArrayList<>();
            personInfo.add(FIO.get(0));
            personInfo.add(FIO.get(1));
            personInfo.add(FIO.get(2));
            personInfo.add(td.get(3).text());
            String marks = td.get(4).text();
            personInfo.add(marks);
            String additionalMarks = td.get(6).text().replaceAll(".*/", "");
            if (additionalMarks.equals("—")) {
                additionalMarks = "0";
            }
            personInfo.add(additionalMarks);
            if (td.get(7).text().equals("+")) {
                personInfo.add("true");
            } else {
                personInfo.add("false");
            }
            entrants.put(id, personInfo);
        }

        List<String> tmp = entrants.get(1068);
        System.out.println(tmp);


        Map<Integer, List<String>> shuffled = new LinkedHashMap<>();
        List<Integer> keys = new ArrayList<>(entrants.keySet());
        Collections.shuffle(keys);
        for (Integer o : keys) {
            shuffled.put(o, entrants.get(o));
        }

        Map<Integer, String[]> cityAndRegions = prepareCitiesRegions();
        Map<Integer, String[]> shuffledCityAndRegions = shuffleCitiesRegions(cityAndRegions);

        PrintWriter pwUsers = new PrintWriter(
                new OutputStreamWriter(
                        new FileOutputStream("sql/INSERT_Users.sql", false), "UTF-8")
        );

        for (Map.Entry<Integer, List<String>> pair : shuffled.entrySet()) {
            List<String> infoEntrants = pair.getValue();
            final int prime_email = 31;
            BigInteger randomNumbersForEmail = BigInteger.valueOf(pair.getKey() * prime_email + 1);
            String password = PasswordHash.randomPassword(8);
            String hashPassword = PasswordHash.createHash(password);
            System.out.println(password + " " + PasswordHash.validatePassword(password, hashPassword));
            String emailGenarated = "entrant" + randomNumbersForEmail + "@gmail.com";
            pwUsers.println(
                    "INSERT INTO Users VALUES (DEFAULT, \'" +
                            hashPassword + "\', \'" +
                            infoEntrants.get(1) + "\', \'" +
                            infoEntrants.get(0) + "\', \'" +
                            infoEntrants.get(2) + "\', \'" +
                            emailGenarated + "\', 2);"
            );
        }
        pwUsers.close();

        int k = 1;
        PrintWriter pwEntrants = new PrintWriter(
                new OutputStreamWriter(
                        new FileOutputStream("sql/INSERT_Entrants.sql", false), "UTF-8")
        );
        while (k < keys.size()) {
            for (Map.Entry<Integer, String[]> pair : shuffledCityAndRegions.entrySet()) {
                List<String> entrantInfo = shuffled.get(k);
                String[] arrayInfo = pair.getValue();
                String city = arrayInfo[0];
                String region = arrayInfo[1];
                String without_competitive_entry = entrantInfo.get(6);
                Random rand = new Random();
                int random_school = rand.nextInt(182) + 1;
                pwEntrants.println(
                        "INSERT INTO Entrants VALUES (DEFAULT, \'" +
                                city + "\', \'" +
                                region + "\', " +
                                random_school + ", " +
                                without_competitive_entry + ", "
                                + k++ + ");"
                );
                if (k == keys.size() + 1) {
                    pwEntrants.close();
                    break;
                }
            }
        }

        PrintWriter pwExtra_Marks = new PrintWriter(
                new OutputStreamWriter(
                        new FileOutputStream("sql/INSERT_Extra_Marks.sql", false), "UTF-8")
        );
        for (Map.Entry<Integer, List<String>> pair : shuffled.entrySet()) {
            List<String> entrantInfo = shuffled.get(pair.getKey());
            String certificate_points = entrantInfo.get(3);
            String extra_points = entrantInfo.get(5);
            pwExtra_Marks.println(
                    "INSERT INTO Extra_Marks VALUES (" +
                            pair.getKey() + ", " +
                            certificate_points + ", " +
                            extra_points +
                            ");");
        }
        pwExtra_Marks.close();


        Map<Integer, String[][]> marks = new LinkedHashMap<>();

        for (Map.Entry<Integer, List<String>> pair : shuffled.entrySet()) {
            List<String> infoEntrants = pair.getValue();
            int entrantId = pair.getKey();
            String[][] subjectIdMarkValue = genarateSubjectIdValue(infoEntrants);
            marks.put(entrantId, subjectIdMarkValue);
        }
        PrintWriter pwMarks = new PrintWriter(
                new OutputStreamWriter(
                        new FileOutputStream("sql/INSERT_Marks.sql", false), "UTF-8")
        );
        Map<Integer, String[][]> shuffledMarks = shuffleMarks(marks);
        System.out.println(marks.keySet().size());
        System.out.println(shuffledMarks.keySet().size());
        for (int entrantId = 1; entrantId <= shuffledMarks.keySet().size(); entrantId++) {
            String[][] subjectIdMarkValue = shuffledMarks.get(entrantId);
            for (String[] aSubjectIdMarkValue : subjectIdMarkValue) {
                for (int j = 0; j < aSubjectIdMarkValue.length; ) {
                    pwMarks.println(
                            "INSERT INTO Marks VALUES (" +
                                    aSubjectIdMarkValue[j++] + ", " +
                                    entrantId + ", " +
                                    aSubjectIdMarkValue[j++] +
                                    ");"
                    );
                }
            }

        }
        pwMarks.close();

        PrintWriter pwFacultiesEntrants = new PrintWriter(
                new OutputStreamWriter(
                        new FileOutputStream("sql/INSERT_Faculties_Entrants.sql", false), "UTF-8")
        );
        for (Map.Entry<Integer, List<String>> pair : shuffled.entrySet()) {
            int entrantId = pair.getKey();
            int marksValue = 0;
            Random randFacultyId = new Random();
            Random randPriority = new Random();
            List<Integer> facultyId = new ArrayList<>();
            facultyId.clear();
            facultyId.add(1);
            facultyId.add(2);
            facultyId.add(3);
            facultyId.add(4);
            facultyId.add(5);
            List<Integer> priority = new ArrayList<>();
            priority.clear();
            priority.add(1);
            priority.add(2);
            priority.add(3);
            for (int i = 0; i < 3; i++) {
                int randomFacultyId = randFacultyId.nextInt(facultyId.size());
                int randomPriority = randPriority.nextInt(priority.size());
                Integer facultyIdInsert = facultyId.get(randomFacultyId);
                Integer priorityInsert = priority.get(randomPriority);

                pwFacultiesEntrants.println(
                        "INSERT INTO Faculties_Entrants VALUES (" +
                                facultyIdInsert + ", " +
                                entrantId + ", " +
                                priorityInsert + ", " +
                                marksValue +
                                ");"
                );
                facultyId.remove(randomFacultyId);
                priority.remove(randomPriority);
            }
        }
        pwFacultiesEntrants.close();
    }

    public static Map<Integer, String[]> prepareCitiesRegions() {
        Map<Integer, String[]> cityAndRegions = new LinkedHashMap<>();
        cityAndRegions.put(1, new String[]{"Харків", "Харівська область"});
        cityAndRegions.put(2, new String[]{"Лозова", "Харівська область"});
        cityAndRegions.put(3, new String[]{"Люботин", "Харівська область"});
        cityAndRegions.put(4, new String[]{"Первомайський", "Харівська область"});
        cityAndRegions.put(5, new String[]{"Чугуїв", "Харівська область"});

        cityAndRegions.put(6, new String[]{"Полтава", "Полтавська область"});
        cityAndRegions.put(7, new String[]{"Кременчук", "Полтавська область"});
        cityAndRegions.put(8, new String[]{"Лубни", "Полтавська область"});
        cityAndRegions.put(9, new String[]{"Миргород", "Полтавська область"});
        cityAndRegions.put(10, new String[]{"Комсомольськ", "Полтавська область"});

        cityAndRegions.put(11, new String[]{"Луганськ", "Луганська область"});
        cityAndRegions.put(12, new String[]{"Алчевськ", "Луганська область"});
        cityAndRegions.put(13, new String[]{"Антрацит", "Луганська область"});
        cityAndRegions.put(14, new String[]{"Брянка", "Луганська область"});
        cityAndRegions.put(15, new String[]{"Красний Луч", "Луганська область"});
        cityAndRegions.put(16, new String[]{"Краснодон", "Луганська область"});
        cityAndRegions.put(17, new String[]{"Первомайськ", "Луганська область"});
        cityAndRegions.put(18, new String[]{"Свердловськ", "Луганська область"});
        cityAndRegions.put(19, new String[]{"Сєверодонецьк", "Луганська область"});

        cityAndRegions.put(20, new String[]{"Донецьк", "Донецька область"});
        cityAndRegions.put(21, new String[]{"Артемівськ", "Донецька область"});
        cityAndRegions.put(22, new String[]{"Горлівка", "Донецька область"});
        cityAndRegions.put(23, new String[]{"Докучаєвськ", "Донецька область"});
        cityAndRegions.put(24, new String[]{"Єнакієве", "Донецька область"});
        cityAndRegions.put(25, new String[]{"Макіївка", "Донецька область"});
        cityAndRegions.put(26, new String[]{"Маріуполь", "Донецька область"});
        cityAndRegions.put(27, new String[]{"Слов''янськ", "Донецька область"});

        return cityAndRegions;
    }

    public static Map<Integer, String[]> shuffleCitiesRegions(Map<Integer, String[]> citiesRegions) {
        Map<Integer, String[]> shuffled = new LinkedHashMap<>();
        List<Integer> keys = new ArrayList<>(citiesRegions.keySet());
        Collections.shuffle(keys);
        for (Integer o : keys) {
            shuffled.put(o, citiesRegions.get(o));
        }
        return shuffled;
    }

    public static Map<Integer, String[][]> shuffleMarks(Map<Integer, String[][]> marks) {
        Map<Integer, String[][]> shuffledMarks = new LinkedHashMap<>();
        List<Integer> keys = new ArrayList<>(marks.keySet());
        int i = 1;
        for (Integer o : keys) {
            if (i == keys.size() + 1) {
                break;
            }
            shuffledMarks.put(o, marks.get(i++));
        }
        List<Integer> keys2 = new ArrayList<>(shuffledMarks.keySet());
        Collections.shuffle(keys2);
        for (Integer o : keys2) {
            shuffledMarks.put(o, shuffledMarks.get(o));
        }
        return shuffledMarks;
    }

    public static String[][] genarateSubjectIdValue(List<String> entrants) {
        String tmp = entrants.get(4).replaceAll("Фізика:", "3");
        tmp = tmp.replaceAll("Матем\\.:", "2");
        tmp = tmp.replaceAll("Укр\\.м.:", "1");
        tmp = tmp.replaceAll("Нім\\.м\\.:", "Англ.м.:");
        tmp = tmp.replaceAll("Фран\\.м\\.:", "Англ.м.:");
        tmp = tmp.replaceAll("Англ\\.м.:", "4");
        String[][] tmps = new String[3][2];
        Pattern p = Pattern.compile("(\\d) (\\d+\\.\\d)");
        Matcher m = p.matcher(tmp);
        int i = 0;
        int j = 0;
        while (m.find()) {
            tmps[i][j++] = m.group(1);
            tmps[i][j] = m.group(2);
            i++;
            j = 0;
        }
        return tmps;
    }
}

