import com.google.common.base.Splitter;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        String input = "Tran Duc Luong";
        List<String> result =  Splitter.on(" ").splitToList(input);
        for (String item: result) {
            System.out.println(item);
        }
        System.out.println("OK");
    }
}
