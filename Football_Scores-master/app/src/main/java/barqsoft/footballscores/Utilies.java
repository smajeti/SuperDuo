package barqsoft.footballscores;

import java.util.HashMap;

/**
 * Created by yehya khaled on 3/3/2015.
 */
public class Utilies
{
//    public static final int SERIE_A = 357;
//    public static final int PREMIER_LEGAUE = 354;
//    public static final int CHAMPIONS_LEAGUE = 362;
//    public static final int PRIMERA_DIVISION = 358;
//    public static final int BUNDESLIGA = 351;

    public static final String BUNDESLIGA1 = "394";
    public static final String BUNDESLIGA2 = "395";
    public static final String LIGUE1 = "396";
    public static final String LIGUE2 = "397";
    public static final String PREMIER_LEAGUE = "398";
    public static final String PRIMERA_DIVISION = "399";
    public static final String SEGUNDA_DIVISION = "400";
    public static final String SERIE_A = "401";
    public static final String PRIMERA_LIGA = "402";
    public static final String BUNDESLIGA3 = "403";
    public static final String EREDIVISIE = "404";
    public static final String CHAMPIONS_LEAGUE = "405";

    private static final HashMap<String, String> leagueCodeMap = initLeagueCode();

    private static HashMap<String, String> initLeagueCode() {
        HashMap<String, String> tempMap = new HashMap<String, String>(20);
        tempMap.put(BUNDESLIGA1, "1. Bundesliga 2015/16");
        tempMap.put(BUNDESLIGA2, "2. Bundesliga 2015/16");
        tempMap.put(LIGUE1, "Ligue 1 2015/16");
        tempMap.put(LIGUE2, "Ligue 2 2015/16");
        tempMap.put(PREMIER_LEAGUE, "Premier League 2015/16");
        tempMap.put(PRIMERA_DIVISION, "Primera Division 2015/16");
        tempMap.put(SEGUNDA_DIVISION, "Segunda Division 2015/16");
        tempMap.put(SERIE_A, "Serie A 2015/16");
        tempMap.put(PRIMERA_LIGA, "Primeira Liga 2015/16");
        tempMap.put(BUNDESLIGA3, "3. Bundesliga 2015/16");
        tempMap.put(EREDIVISIE, "Eredivisie 2015/16");
        tempMap.put(CHAMPIONS_LEAGUE, "Champions League 2015/16");

        return tempMap;
    }

    public static boolean isValidLeagueCode(String leagueCode) {
        return leagueCodeMap.containsKey(leagueCode);
    }

    public static String getLeague(String league_num) {
        if (leagueCodeMap.containsKey(league_num)) {
            return leagueCodeMap.get(league_num);
        }

        return "Not known League Please report";
    }

    public static String getMatchDay(int match_day, String league_num)
    {
        if(league_num.equals(CHAMPIONS_LEAGUE))
        {
            if (match_day <= 6)
            {
                return "Group Stages, Matchday : 6";
            }
            else if(match_day == 7 || match_day == 8)
            {
                return "First Knockout round";
            }
            else if(match_day == 9 || match_day == 10)
            {
                return "QuarterFinal";
            }
            else if(match_day == 11 || match_day == 12)
            {
                return "SemiFinal";
            }
            else
            {
                return "Final";
            }
        }
        else
        {
            return "Matchday : " + String.valueOf(match_day);
        }
    }

    public static String getScores(int home_goals,int awaygoals)
    {
        if(home_goals < 0 || awaygoals < 0)
        {
            return " - ";
        }
        else
        {
            return String.valueOf(home_goals) + " - " + String.valueOf(awaygoals);
        }
    }

    public static int getTeamCrestByTeamName (String teamname)
    {
        if (teamname==null){return R.drawable.no_icon;}
        switch (teamname)
        { //This is the set of icons that are currently in the app. Feel free to find and add more
            //as you go.
            case "Arsenal London FC" : return R.drawable.arsenal;
            case "Manchester United FC" : return R.drawable.manchester_united;
            case "Swansea City" : return R.drawable.swansea_city_afc;
            case "Leicester City" : return R.drawable.leicester_city_fc_hd_logo;
            case "Everton FC" : return R.drawable.everton_fc_logo1;
            case "West Ham United FC" : return R.drawable.west_ham;
            case "Tottenham Hotspur FC" : return R.drawable.tottenham_hotspur;
            case "West Bromwich Albion" : return R.drawable.west_bromwich_albion_hd_logo;
            case "Sunderland AFC" : return R.drawable.sunderland;
            case "Stoke City FC" : return R.drawable.stoke_city;
            default: return R.drawable.no_icon;
        }
    }
}
