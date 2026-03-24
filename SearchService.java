import java.util.*;

public class SearchService {

    private Map<String, List<String>> index = new HashMap<>();

    public void indexContent(String user, String file, String content) {
        String fullName = user + "_" + file;

        String[] words = content.toLowerCase().split("\\W+");

        for (String w : words) {
            index.putIfAbsent(w, new ArrayList<>());

            if (!index.get(w).contains(fullName)) {
                index.get(w).add(fullName);
            }
        }
    }

    public List<String> search(String keyword, String user) {
        List<String> results = index.getOrDefault(keyword.toLowerCase(), new ArrayList<>());

        List<String> filtered = new ArrayList<>();

        for (String f : results) {
            if (f.startsWith(user + "_")) {
                filtered.add(f);
            }
        }

        return filtered;
    }

    public void removeFile(String fullName) {
        for (String key : index.keySet()) {
            index.get(key).remove(fullName);
        }
    }
}