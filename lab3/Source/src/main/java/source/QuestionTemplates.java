package source;

public class QuestionTemplates {

    public static String getTemplate1(String qi) {
       String s = Initializer.r.getString("Q1temp");
        s = String.format(s, qi);

        return s;

    }

    public static String getAnswer1(String qi, String ea, boolean answer) {

        String s = "";
        if (answer) {
            s = Initializer.r.getString("Q1ansT");
            s = String.format(s, qi, ea);
        } else {
            s = Initializer.r.getString("Q1ansF");
            s = String.format(s, qi, ea);
        }

        return s;
    }

    public static String getTemplate2(String qi) {
        String s = Initializer.r.getString("Q2temp");
        s = String.format(s, qi);

        return s;

    }

    public static String getAnswer2(String qi, String ea, boolean answer) {

        String s = "";
        if (answer) {
            s = Initializer.r.getString("Q2ansT");
            s = String.format(s, qi, ea);
        } else {
            s = Initializer.r.getString("Q2ansF");
            s = String.format(s, qi, ea);
        }

        return s;

    }

    public static String getTemplate3(String qi) {
        String s = Initializer.r.getString("Q3temp");
        s = String.format(s, qi);

        return s;

    }

    public static String getAnswer3(String qi, String ea, boolean answer) {

        String s = "";
        if (answer) {
            s = Initializer.r.getString("Q3ansT");
            s = String.format(s, ea, qi);
        } else {
            s = Initializer.r.getString("Q3ansF");
            s = String.format(s, ea, qi);
        }

        return s;
    }

    public static String getTemplate4(String qi) {
        String s = Initializer.r.getString("Q4temp");
        s = String.format(s, qi);

        return s;

    }

    public static String getAnswer4(String qi, String ea, boolean answer) {

        String s = "";
        if (answer) {
            s = Initializer.r.getString("Q4ansT");
            s = String.format(s, ea, qi);
        } else {
            s = Initializer.r.getString("Q4ansF");
            s = String.format(s, ea, qi);
        }

        return s;


    }

    public static String getTemplate5(String qi) {
        String s = Initializer.r.getString("Q5temp");
        s = String.format(s, qi);

        return s;

    }

    public static String getAnswer5(String qi, String ea, boolean answer) {
        String s = "";
        if (answer) {
            s = Initializer.r.getString("Q5ansT");
            s = String.format(s, qi, ea);
        } else {
            s = Initializer.r.getString("Q5ansF");
            s = String.format(s, qi, ea);
        }

        return s;
    }


}
