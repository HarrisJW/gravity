package dal.gravity;

import java.text.NumberFormat;

/** 
 * compares the values of a simple pendulum using the harmonic motion equation
 * versus the Euler algorithm approximation
 */
public class PendulumRunner {
	
	private final static double EARTH_G = 9.80665;
	private final static double JUPITER_G = 25.00000;

    public static void main (String [] args) {
    	
    	NumberFormat nf = NumberFormat.getInstance ();
    	nf.setMaximumFractionDigits (3);

    	double delta = (args.length == 0) ? .1 : Double.parseDouble (args[0]);
    	double sLen = 10, pMass = 10, theta0 = Math.PI/30;
    	
    	RegularPendulum rp = new RegularPendulum (sLen, pMass, theta0, new GravityConstant(EARTH_G), delta);
    	SimplePendulum sp = new SimplePendulum (sLen, pMass, theta0, new GravityConstant(EARTH_G));
    	
    	// print out difference in displacement in 1 second intervals
    	// for 20 seconds
    	
    	int iterations = (int) (1/delta);
    	System.out.println("Planet Earth:\n");
    	System.out.println ("analytical vs. numerical displacement (regularPendulum, simplePendulum)");
    	
    	for (int second = 1; second <= 20; second++) {
    		
    		for (int i = 0; i < iterations; i++) rp.step ();

    		System.out.println ("t=" + second + "s: \t" + 
    		
    				nf.format (Math.toDegrees (sp.getTheta (second))) 
					+ "\t" +
					nf.format (Math.toDegrees (rp.getLastTheta ())));
    	}
    	
    	// Adjust gravitational field dynamically.
    	sp.setGravitationalField(new GravityConstant(JUPITER_G));
    	rp.setGravitationalField(new GravityConstant(JUPITER_G));
    	
      	System.out.println("\nPlanet Jupiter:\n");
    	System.out.println ("analytical vs. numerical displacement (regularPendulum, simplePendulum)");
    	
    	for (int second = 1; second <= 20; second++) {
    		
    		for (int i = 0; i < iterations; i++) rp.step ();

    		System.out.println ("t=" + second + "s: \t" + 
    		
    				nf.format (Math.toDegrees (sp.getTheta (second))) 
					+ "\t" +
					nf.format (Math.toDegrees (rp.getLastTheta ())));
    	}
    	
    }
}

