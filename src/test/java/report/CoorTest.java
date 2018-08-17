package report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoorTest {

	
	public static void main(String[] args) {
		
		List<String> a = Arrays.asList("30998,31811,32921,34060,34755,35790,36440,37364,38255,38803,39506,40075,40424,40839,41062,41216,41316,41315,41312,41146,41061,40856,40617,40468,40136,39917,39579,39217,38950,38565,38246,38054,37869,37672,37603,37560,37530,37564,37612,37688,37821,38006,38130,38358,38574,38893,39249,39526,39869,40299,40544,40920,41257,41463,41814,42040,42337,42617,42838,43162,43448,43591,43857,44048,44304,44496,44604,44815,45008,45174,45404,45585,45793,46035,46255,46541,46816,47033,47402,47700,47877,48170,48534,48710,49012,49170,49518,49844,49998,50237,50516,50633,50862,50913,50968,50961,50819,50397,50175,50222,50526,50968,51279,51731,52049,52573,53004,53257,53649,54045,54260,54595,54766,55047,55268,55456,55601,55693,55748,55853,55932,55947,55907,55861,55821,55656,55558,55296,55012,54801,54520,54276,54071,53910,53881,53882,53938,53910,53887,53780,53653,53430,53307,53136,52900,52775,52668,52542,52474,52356,52250,52190,52114,52024,51874,51717,51621,51412,51177,51004,50782,50607,50291,49997,49800,49494,49186,48944,48600,48285,48069,47735,47495,47140,46731,46511,46117,45720,45486,45096,44820,44458,44084,43856,43542,43147,42851,42370,41890,41535,41053,40699,40177,39696,39332,38795,38233,37850,37333,36821,36457,35946,35606,35031,34501,34133,33586,33078,32710,32098,31718,31148,30498,30096,29420,28801,28368,27742,27111,26720,26060,25670,24999,24370,23937,23284,22620,22189".split(","));
		List<String> b = Arrays.asList("30981,31807,32928,34034,34741,35776,36418,37329,38259,38801,39458,40054,40409,40795,41034,41229,41283,41301,41280,41144,41024,40836,40586,40456,40120,39901,39550,39202,38918,38567,38208,38045,37847,37694,37581,37527,37529,37568,37607,37667,37799,37991,38103,38369,38554,38881,39269,39480,39864,40274,40513,40876,41250,41448,41800,42028,42334,42594,42819,43141,43424,43583,43832,44039,44291,44470,44548,44789,44975,45128,45370,45590,45789,46034,46216,46491,46800,47030,47378,47681,47861,48150,48542,48712,48994,49182,49479,49817,49991,50251,50489,50611,50854,50891,50944,50936,50802,50375,50139,50177,50506,50949,51235,51731,52032,52533,52973,53220,53604,54012,54235,54573,54733,55024,55263,55437,55585,55665,55727,55861,55930,55920,55888,55844,55777,55645,55516,55253,54981,54780,54474,54287,54042,53890,53848,53819,53906,53931,53872,53751,53612,53434,53291,53102,52890,52762,52634,52527,52451,52327,52246,52181,52110,52006,51856,51714,51594,51398,51155,51008,50780,50603,50299,49973,49773,49473,49138,48927,48581,48268,48060,47697,47471,47101,46714,46507,46088,45685,45472,45077,44826,44429,44050,43854,43523,43143,42844,42383,41859,41514,41013,40706,40165,39671,39323,38780,38232,37858,37319,36772,36462,35919,35574,35028,34487,34134,33583,33083,32694,32103,31689,31142,30510,30078,29427,28811,28366,27724,27097,26699,26045,25653,25020,24342,23915,23281,22615,22183".split(","));
		
		
		List<Double> aa =  new ArrayList<>();
		a.forEach(l->aa.add(Double.valueOf(l)));
		List<Double> bb =  new ArrayList<>();
		b.forEach(l->bb.add(Double.valueOf(l)));
		
		double coor = absCorrelation(aa,bb);
		double coor1 = absCorrelation(bb,aa);
		
		System.out.println(coor);
		System.out.println(coor1);
		
		
		
	}
	
	
	
	public static double absCorrelation(List<Double> lastTimeAvgAbs,List<Double> nowAvgAbs){

        double avgX = getAvg(lastTimeAvgAbs);                   // 上一次的平均吸光度平均值

        double avgY =  getAvg(nowAvgAbs);                       // 当前的平均吸光度平均值

        double var_last = 0;

        int size = nowAvgAbs.size();    // 波长数

        double sum =0;
        double var_now = 0;

        for ( int i = 0; i < size; i++ ) {

            double lv = lastTimeAvgAbs.get(i) - avgX;
            var_last+=Math.pow(lv,2);

            double cv = nowAvgAbs.get(i) - avgY;
            var_now+=Math.pow(cv,2);
            sum+= (lv*cv);
        }

        return sum/(Math.sqrt(var_last)*Math.sqrt(var_now));

    }

	
	private static double getAvg(List<Double> abs){
		float avg = 0;
		for (Double value : abs) {
			avg+=value;
		}
		return avg/abs.size();
	}
	
	
}