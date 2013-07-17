package MathLogic.old;

/**
 * User: Patrick
 * Date: 7/13/13
 * Time: 11:11 AM
 */
public class Math {

    public int add(int... z) {
        int answer = z[0];
        for(int i=1;i<z.length;i++)
            answer += z[i];
        return answer;
    }

    public int subtract(int... z) {
        int answer = z[0];
        for(int i=1;i<z.length;i++)
            answer -= z[i];
        return answer;
    }
}
