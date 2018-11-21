package fuzzyAllocation;

import java.io.IOException;


public class MyRunner {
	public static void main(String[] args) throws IOException {
		boolean enableOutput = true;
		boolean outputToFile = true;
		String inputFolder = MyNonPowerAware.class.getClassLoader().getResource("workload/planetlab").getPath();
		String outputFolder = "output1007";
		String workload = "My20110303"; // PlanetLab workload
		String vmAllocationPolicy = "thr"; // Static Threshold (THR) VM allocation policy
		String vmSelectionPolicy = "mc"; // Maximum Correlation (MC) VM selection policy
		String parameter = "0.8"; // the static utilization threshold

		new MyPlanetLabRunner(
				enableOutput,
				outputToFile,
				inputFolder,
				outputFolder,
				workload,
				vmAllocationPolicy,
				vmSelectionPolicy,
				parameter);
	}
	
}

