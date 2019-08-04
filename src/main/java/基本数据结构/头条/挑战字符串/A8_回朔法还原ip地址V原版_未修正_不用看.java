package 基本数据结构.头条.挑战字符串;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 自创基于两个栈的back forward模型,处理回溯问题
 */
public class A8_回朔法还原ip地址V原版_未修正_不用看 {

    public  List<List<String>> lists = new ArrayList<>();

    public static final int DEPTH = 4;
    public static final int WIDTH = 3;

    public  void next(LinkedList<String> dest, LinkedList<String> origin, boolean end) {
        //1 结束,则执行回溯,结束意味着数据不足,或者太多,或者不匹配(比如以0开头)
        if (end) {
            if (dest.size() == 0) {
                return;
            }
            String str = headToOrigin(dest, origin);
            if (str.length() == WIDTH) {
//                if (dest.size() == 0) {//终止条件,dest栈空
//                    return;
//                }
                next(dest, origin, true);
                return;
            } else {
                if (origin.size() < str.length() + 1) {
                    next(dest, origin, true);//dest数量不足,回退
                    return;
                }
                headFromOrigin(dest, origin, str.length() + 1);
                next(dest, origin, false);
                return;
            }
        }
        int depth = dest.size();

        int width = 0;//null todo
        if (dest.size() != 0) {
            width = dest.getFirst().length();
        }

        //2. 深度没有到达
        if (depth != DEPTH) {
            if (origin.size() == 0) {
                headToOrigin(dest, origin);
                next(dest, origin, true);
                return;
            }
            headFromOrigin(dest, origin, 1);
            next(dest, origin, false);
            return;
        }
        //3. 广度没有到达,(此处需要进行合法性校验)
        else if (width != WIDTH) {
            if (origin.isEmpty()) {
                lists.add(deepCopy(dest, LinkedList.class));
                //数据写会origin, 执行回溯
                headToOrigin(dest, origin);
                next(dest, origin, true);
                return;
            } else {
                //增加广度
                String s = headToOrigin(dest, origin);
                if (origin.size() < s.length() + 1) {
                    next(dest, origin, true);//扩容操作,数据数量校验
                    return;
                }
                headFromOrigin(dest, origin, s.length() + 1);
                next(dest, origin, false);//继续增加广度
                return;
            }
        }
        //4. 深度广度同时到达
        else if (depth == DEPTH && WIDTH == width) {
            if (origin.isEmpty()) {
                lists.add(deepCopy(dest, LinkedList.class));
            }
            headToOrigin(dest, origin);
            next(dest, origin, true);//回溯
        }

    }

    //abc->a b c,非扩容操作不需要校验
    public  String headToOrigin(LinkedList<String> dest, LinkedList<String> origin) {
        String s = dest.removeFirst();
        for (int i = 0; i < s.length(); i++) {
            origin.addFirst(String.valueOf(s.charAt(s.length() - 1 - i)));
        }
        return s;
    }

    //扩容操作,数据数量校验
    public  void headFromOrigin(LinkedList<String> dest, LinkedList<String> origin, int len) {
        String str = "";
        for (int i = 0; i < len; i++) {
            String s = origin.removeFirst();
            str = str.concat(s);
        }
        dest.addFirst(str);
    }

    /**
     * 深复制
     */

    public static <T> T deepCopy(T instance, Class<T> clazz) {
        T instanceCopy = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(instance);
            oos.close();

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            instanceCopy = (T) ois.readObject();
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return instanceCopy;
    }

    public  List<String> restoreIpAddresses(String s) {
        if (s == null || s.length() == 0) {
            return new ArrayList<>();
        }
        LinkedList<String> dest = new LinkedList<>();
        LinkedList<String> origin = new LinkedList<>();

        for (int i = 0; i < s.length(); i++) {
            origin.addFirst(String.valueOf(s.charAt(s.length() - 1 - i)));
        }

        next(dest, origin, false);

        for (List<String> list : lists) {
            //
            Collections.reverse(list);
        }

//        for (int i = 0; i < lists.size(); i++) {
//            List<String> strings = lists.get(i);
//            for (String string : strings) {
//                if (string.startsWith("0")&&string.length()>1) {
//                    lists.remove(i);
//                    continue;
//                }
//                int value = Integer.valueOf(string);
//                if (value>255||value<0) {
//                    lists.remove(i);
//                    continue;
//                }
//            }
//
//            Collections.reverse(lists.get(i));
//
//        }

        List<String> rst = new ArrayList<>();
        for (List<String> one : lists) {
            String str = "";
            boolean stop = false;
            for (String s1 : one) {
                if (s1.startsWith("0")&&s1.length()>1) {
                    stop = true;
                    break;
                }
                int value = Integer.valueOf(s1);
                if (value>255||value<0) {
                    stop = true;
                    break;
                }
                str = str.concat(s1).concat(".");
            }
            if (stop) {
                continue;
            }
            str = str.substring(0, str.length() - 1);
            rst.add(str);
        }
        return rst;
    }

    public static void main(String[] args) {
        System.out.println(new A8_回朔法还原ip地址V原版_未修正_不用看().restoreIpAddresses("00000"));
    }
}
