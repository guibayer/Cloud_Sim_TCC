package fuzzyAllocation;

import java.util.Calendar;

import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.core.CloudSim;

public class MyPlanetLabRunner extends MyRunnerAbstract{

	public MyPlanetLabRunner(
			boolean enableOutput,
			boolean outputToFile,
			String inputFolder,
			String outputFolder,
			String workload,
			String vmAllocationPolicy,
			String vmSelectionPolicy,
			String parameter) {
		super(
				enableOutput,
				outputToFile,
				inputFolder,
				outputFolder,
				workload,
				vmAllocationPolicy,
				vmSelectionPolicy,
				parameter);
	}

	@Override
	protected void init(String inputFolder) {
		try {
			CloudSim.init(1, Calendar.getInstance(), false);

			broker = MyHelper.createBroker();
			int brokerId = broker.getId();

			cloudletList = MyPlanetLabHelper.createCloudletListPlanetLab(brokerId, inputFolder);
			vmList = MyHelper.createVmList(brokerId, cloudletList.size());
			System.out.println("Quantidade de VMs: " + vmList.size() + " e Cloudlets: " + cloudletList.size());
			hostList = MyHelper.createHostList(MyPlanetLabConstants.NUMBER_OF_HOSTS);
			System.out.println("Tenho " + hostList.size() + " Hosts");
		} catch (Exception e) {
			e.printStackTrace();
			Log.printLine("The simulation has been terminated due to an unexpected error");
			System.exit(0);
		}
	}
	
}
