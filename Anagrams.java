import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Anagrams {




    public static Boolean wordCheck(String word, String key){  //для поиска анаграмм по ключу
        if (word.length()!=key.length()) return false;         // реализацию надежнее для случая с несколькими
        boolean result = true;                                 // совпадающими буквами в словах не придумал
            char[] c = key.toCharArray();
                Arrays.sort(c);
            char[] c1 = word.toCharArray();
                Arrays.sort(c1);
            for (int i = 0; i< c.length;i++){
                if (c[i]!=c1[i]) result = false;
            }
            return result;
    };

    public static LinkedHashMap Finalise (LinkedHashMap <TreeSet<String>, Integer> map) {  
        LinkedHashMap <TreeSet<String>, Integer> result = new LinkedHashMap();
        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
        return  result;
    }

    public static void main(String[] args) {  //начало



        String reg = "(\\P{javaAlphabetic})"; //фильтр символов и пробелов


        LinkedHashMap<TreeSet<String>, Integer> finalResult= new LinkedHashMap<>();
        String anagramKey = new String();
        Stream<String> data = Arrays.stream(args)


                                .map(s -> s.replaceAll(reg, ""))
                                .filter(s -> !s.isBlank())
                                .filter(s -> !s.isEmpty())
                                .map(s -> s.toLowerCase());

//конец потока, преобразование в LinkedList

        List <String> intermediateResult = data.collect(Collectors.toList());

//поиск и подбор анаграмм

        while(intermediateResult.size()>1) { //пока есть слова
            TreeSet<String> result = new TreeSet<>();
            String key = intermediateResult.get(intermediateResult.size() - 1); //берем из слов ключ
            result.add(key);
            intermediateResult.remove(intermediateResult.size() - 1); //убираем его из списка

            for (int i = intermediateResult.size() - 1; i >= 0; i -- ) {   //ищем анаграммы
                String s = intermediateResult.get(i);

                if (wordCheck(s, key)) {
                    result.add(s);                      //нашли - добавляем в TreeSet к ключу
                    intermediateResult.remove(s);
                }
            }
            finalResult.put(result, result.size()); //формируем Map с ключом TreeSet и значением его размера

        }

        System.out.println(Finalise(finalResult).toString());

    }

}
