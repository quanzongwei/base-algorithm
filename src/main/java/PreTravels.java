/**
 * Created by Administrator on 2019/2/28 0028.
 */
public class PreTravels {
    public static void main(String[] args) {
        int[] arr = {2, 3, 5, 1};
        bubble(arr);
        System.out.println(arr);

    }

    public static void bubble(int[] arr) {

        int n = arr.length;
        //n-1次排序
        for (int i = 0; i < n; i++) {
            int x = n - i - 1-1;
            int j = -1;
            while (j != x) {
                j++;
                if (arr[j]>arr[j+1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                }
            }
        }
    }

    public void scan(MyNode node) {
        System.out.println(node.value);
        scan(node.lChild);
        scan(node.rChild);
    }

    public static class MyNode{
        int value;
        MyNode lChild;
        MyNode rChild;

    }

}
