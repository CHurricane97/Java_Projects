package source;

import java.util.Random;

public class Quiz {

    public Question generateRandomQuestion(){
        Random random = new Random();
        int tempNum = random.nextInt(5);
        Question question = new Question();
        String url = "https://api.teleport.org/api/countries/";

        int count = ServiceOperations.getCountryCount(url);

        int i = random.nextInt(count);

        String name = ServiceOperations.getCountryNameFromList(url, i);
        String href = ServiceOperations.getCountryHrefFromList(url, i);

        switch (tempNum){
            case 0:

                //Na jakim kontynencie leży (państwo)

                String continent = ServiceOperations.getContinentFromCountry(href);

                question.setAnswer(continent);
                question.setContent(QuestionTemplates.getTemplate1(name));
                question.setAnswerGood(QuestionTemplates.getAnswer1(name, continent, true));
                question.setAnswerBad(QuestionTemplates.getAnswer1(name, continent, false));

                break;
            case 1:

                //Ile wynosi populacja kraju (kraj)? (Można się pomylić o 10%)

                long population = ServiceOperations.getCountryPopulation(href);

                question.setContent(QuestionTemplates.getTemplate2(name));
                question.setAnswer(Long.toString(population));
                question.setAnswerGood(QuestionTemplates.getAnswer2(name, Long.toString(population), true));
                question.setAnswerBad(QuestionTemplates.getAnswer2(name, Long.toString(population), false));

                break;
            case 2:

                //Jaka waluta obowiązuje w kraju (kraj)? (W odpowiedzi podaj kod waluty, np: \"USD\")

                String currency = ServiceOperations.getCountryCurrency(href);

                question.setContent(QuestionTemplates.getTemplate3(name));
                question.setAnswer(currency);
                question.setAnswerGood(QuestionTemplates.getAnswer3(name, currency, true));
                question.setAnswerBad(QuestionTemplates.getAnswer3(name, currency, false));

                break;
            case 3:

                //Ile jednostek administracyjnych poziomu pierwszego istnieje w państwie(państwo)?

                int admin = ServiceOperations.getAdministrative(href);

                question.setAnswer(Integer.toString(admin));
                question.setContent(QuestionTemplates.getTemplate4(name));
                question.setAnswerGood(QuestionTemplates.getAnswer4(name, Integer.toString(admin), true));
                question.setAnswerBad(QuestionTemplates.getAnswer4(name, Integer.toString(admin), false));

                break;
            case 4:

                //Ile państw znajduje się w (kontynent)?

                url = "https://api.teleport.org/api/continents/";
                count = ServiceOperations.getContinentCount(url);
                i = random.nextInt(count);
                name = ServiceOperations.getContinentNameFromList(url, i);
                href = ServiceOperations.getContinentHrefFromList(url, i);

                int countContinent = ServiceOperations.getCountryCountOnContinent(href + "countries/");

                question.setContent(QuestionTemplates.getTemplate5(name));
                question.setAnswer(Long.toString(countContinent));
                question.setAnswerGood(QuestionTemplates.getAnswer5(name, Long.toString(countContinent), true));
                question.setAnswerBad(QuestionTemplates.getAnswer5(name, Long.toString(countContinent), false));

                break;
        }
        return question;
    }



    public static void main(String[] args) {
        Quiz q = new Quiz();
        for (int i = 0; i < 20; i++){
            Question qu = q.generateRandomQuestion();
            System.out.println(qu.getContent());
            System.out.println(qu.getAnswer());
        }
    }
}
