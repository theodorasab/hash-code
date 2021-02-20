package hashcodepractice;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class main {
	static int total_score = 0;
	//static int max_tries = 562841;
	
	 public static long factorial(int number) {
	        long result = 1;

	        for (int factor = 2; factor <= number; factor++) {
	            result *= factor;
	        }

	        return result;
	 }
	 
	 public static int[] generate(int n, int r, List<Pizza> pizzas) {
		    List<int[]> combinations = new ArrayList<>();
		    int[] combination = new int[r];
		    List<Integer> sentPizzas = new ArrayList<>(); 
			List<Pizza> toRemove = new ArrayList<>();
			List<String> ingredients = new ArrayList<>();
			List<String> local_ingredients = new ArrayList<>();
			Random rand = new Random();
		    int count = 0;
			int maxScore = -1;
			int[] maxPizzas = null;
			int total = 0;

		    // initialize with lowest lexicographic combination
		    for (int i = 0; i < r; i++) {
		        combination[i] = i;
		        maxPizzas = combination.clone();
		    }

//		    while (combination[r - 1] < n && count++ < max_tries) {
//		        //combinations.add(combination.clone());
//		    	int score = 0;
//				local_ingredients = new ArrayList<>();
//						
//				for(int j = 0; j < combination.length; j++) {
//					//score += pizzas.get(p[j]).getScore();
//					
//					for(String s : pizzas.get(combination[j]).getIngredients()) {
//						if(!local_ingredients.contains(s)) {
//							score++;
//							local_ingredients.add(s);
//						}
//					}
//				}
//				
//				if(score > maxScore) {
//					maxScore = score;
//					maxPizzas = combination.clone();
//				}
//
//		         // generate next combination in lexicographic order
//		        int t = r - 1;
//		        while (t != 0 && combination[t] == n - r + t) {
//		            t--;
//		        }
//		        combination[t]++;
//		        for (int i = t + 1; i < r; i++) {
//		            combination[i] = combination[i - 1] + 1;
//		        }
//		    }

		    return maxPizzas;
	}
	
	public static List<Integer> servePizzas(List<Pizza> pizzas, int memberCount) {
		List<Integer> sentPizzas = new ArrayList<>(); 
		List<Pizza> toRemove = new ArrayList<>();
		List<String> ingredients = new ArrayList<>();
		List<String> local_ingredients = new ArrayList<>();
		List<Integer> usedRandoms = new ArrayList<>();
		
		Random rand = new Random();
		int maxScore = -1;
		//int[] maxPizzas = null;
		int[] maxPizzas = new int[memberCount];
		int total = 0;

		if(memberCount > pizzas.size())
			return null;
		
		
		//maxPizzas = generate(pizzas.size(), memberCount, pizzas);
//		for(int i = 0; i < selection.size(); i++) {
//			int score = 0;
//			int[] p = selection.get(i);
//			local_ingredients = new ArrayList<>();
//					
//			for(int j = 0; j < p.length; j++) {
//				//score += pizzas.get(p[j]).getScore();
//				
//				for(String s : pizzas.get(p[j]).getIngredients()) {
//					if(!local_ingredients.contains(s)) {
//						score++;
//						local_ingredients.add(s);
//					}
//				}
//			}
//			
//			if(score > maxScore) {
//				maxScore = score;
//				maxPizzas = p;
//			}
//		}
		
		for (int i = 0; i < memberCount; i++) {
			int myRandom = rand.nextInt(pizzas.size());
			while(usedRandoms.contains(myRandom)) {
				myRandom = rand.nextInt(pizzas.size());
			}
			usedRandoms.add(myRandom);
	        maxPizzas[i] = myRandom;
	    }
		
		//System.out.println(maxPizzas.length + " " + pizzas.size());
		for(int i = 0; i < maxPizzas.length; i++) {
			sentPizzas.add(pizzas.get(maxPizzas[i]).getId());
			
			for(String s : pizzas.get(maxPizzas[i]).getIngredients()) {
				if(!ingredients.contains(s)) {
					total++;
					ingredients.add(s);
				}
			}
			
			toRemove.add(pizzas.get(maxPizzas[i]));
		}
		
		total_score += Math.pow(total, 2);
		pizzas.removeAll(toRemove);
		
		return sentPizzas;
	}
	
	public static Pizza createPizza(String data, int index) {
		Pizza pizza = new Pizza();
		String[] d = data.split(" ");
		
		pizza.setId(index);
		
		for(int i = 1; i < d.length; i++) {
			pizza.addIngredient(d[i]);
		}
		
		return pizza;
	}

	public static void main(String[] args) {
		int lineCounter = 0, totalPizzasDelivered = 0, totalPizzas, two_member_team = 0, three_member_team = 0, four_member_team = 0;
		boolean teamsLeft = true;
		List<Pizza> pizzas = new ArrayList<>();
		
		// Read
		try {
			File file = new File("D:\\Downloads\\d_many_pizzas.in");
			FileWriter output = new FileWriter("output.txt");
			StringBuilder pizzaData = new StringBuilder();
			
			Scanner sc = new Scanner(file);
			
			while(sc.hasNextLine()) {
				String data = sc.nextLine();
				
				if(lineCounter == 0) {
					String[] d = data.split(" ");
					totalPizzas = Integer.parseInt(d[0]);
					two_member_team = Integer.parseInt(d[1]);
					three_member_team = Integer.parseInt(d[2]);
					four_member_team = Integer.parseInt(d[3]);
					
					lineCounter++;
					continue;
				}
				
				pizzas.add(createPizza(data, lineCounter++ - 1));
				
			}
			
			while(teamsLeft) {
				List<Integer> sentPizzas = null;
				System.out.println("2: " + two_member_team + " 3: " + three_member_team + " 4: " + four_member_team);
				
				// Serve two member team
				if(two_member_team > 0) {
						
					sentPizzas = servePizzas(pizzas, 2);
//					if(sentPizzas != null)
//						for(Integer o : sentPizzas) {
//							System.out.println(o);
//						}
					if(sentPizzas != null) {
						totalPizzasDelivered++;
						pizzaData.append("2 ");
						for(Integer o : sentPizzas) {
							pizzaData.append(o + " ");
						}
						pizzaData.deleteCharAt(pizzaData.length() - 1);
						pizzaData.append("\n");
					}
					
					
					if((pizzas.size() - 2 > 0 && pizzas.size() - 2 < 2) && (pizzas.size() % 3 == 0 || pizzas.size() % 4 == 0))
						two_member_team = 1;

					two_member_team--;
					continue;
				}
				
				// Serve three member team
				if(three_member_team > 0) {
					
					sentPizzas = servePizzas(pizzas, 3);
//					if(sentPizzas != null)
//						for(Integer o : sentPizzas) {
//							System.out.println(o);
//						}
					
					if(sentPizzas != null) {
						totalPizzasDelivered++;
						pizzaData.append("3 ");
						for(Integer o : sentPizzas) {
							pizzaData.append(o + " ");
						}
						pizzaData.deleteCharAt(pizzaData.length() - 1);
						pizzaData.append("\n");
					}
					
					if((pizzas.size() - 3 > 0 && pizzas.size() - 3 < 3) && (pizzas.size() % 2 == 0 || pizzas.size() % 4 == 0))
						three_member_team = 1;

					three_member_team--;
					continue;
				}
				
				// Serve four member team
				if(four_member_team > 0) {
					
					sentPizzas = servePizzas(pizzas, 4);
//					if(sentPizzas != null)
//						for(Integer o : sentPizzas) {
//							System.out.println(o);
//						}
					
					if(sentPizzas != null) {
						totalPizzasDelivered++;
						pizzaData.append("4 ");
						for(Integer o : sentPizzas) {
							pizzaData.append(o + " ");
						}
						pizzaData.deleteCharAt(pizzaData.length() - 1);
						pizzaData.append("\n");
					}
					
					if((pizzas.size() - 4 > 0 && pizzas.size() - 4 < 4) && (pizzas.size() % 2 == 0 || pizzas.size() % 3 == 0))
						four_member_team = 1;
					
					four_member_team--;
					continue;
				}

				if((two_member_team == 0 && three_member_team == 0 && four_member_team == 0) || pizzas.size() == 0)
					teamsLeft = false;
			}
			sc.close();	
			pizzaData.deleteCharAt(pizzaData.length() - 1);
			System.out.println(pizzaData);
			
			output.write(totalPizzasDelivered + "\n" + pizzaData);
			output.close();
			
			System.out.println("Total score: " + total_score);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
