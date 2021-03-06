package fuzzyAllocation;

import org.cloudbus.cloudsim.power.models.PowerModel;
import org.cloudbus.cloudsim.power.models.PowerModelSpecPowerHpProLiantMl110G5Xeon3075;

/**
 * If you are using any algorithms, policies or workload included in the power package, please cite
 * the following paper:
 *
 * Anton Beloglazov, and Rajkumar Buyya, "Optimal Online Deterministic Algorithms and Adaptive
 * Heuristics for Energy and Performance Efficient Dynamic Consolidation of Virtual Machines in
 * Cloud Data Centers", Concurrency and Computation: Practice and Experience (CCPE), Volume 24,
 * Issue 13, Pages: 1397-1420, John Wiley & Sons, Ltd, New York, USA, 2012
 *
 * @author Anton Beloglazov
 * @since Jan 6, 2012
 */

public class MyConstants {

	public final static boolean ENABLE_OUTPUT = true;
	public final static boolean OUTPUT_CSV    = false;

	public final static double SCHEDULING_INTERVAL = 300;
	public final static double SIMULATION_LIMIT = 24 * 60 * 60;

	public final static int CLOUDLET_LENGTH	= 2500 * (int) SIMULATION_LIMIT;
	public final static int CLOUDLET_PES	= 1;

	/*
	 * Instance types based on aws values: https://aws.amazon.com/pt/ec2/instance-types/
	 * SPEC values based on link: https://www.spec.org/power_ssj2008/results/res2017q3/power_ssj2008-20170829-00780.html 
	 * MIPS VALUE FOR Intel Xeon Platinum 8180 2.50 GHz
	 * 3720 MIPS - 54.479     ssj_ops - Base values as used on Hosts definition
	 * x         - 5,727,798  ssj_ops
	 * x = 391112 MIPS / 56 cores = 6170 MIPS per vCPU
	 * Old VM instance types:
	 *   High-Memory Extra Large Instance: 3.25 EC2 Compute Units, 8.55 GB // too much MIPS
	 *   High-CPU Medium Instance: 2.5 EC2 Compute Units, 0.85 GB
	 *   Extra Large Instance: 2 EC2 Compute Units, 3.75 GB
	 *   Small Instance: 1 EC2 Compute Unit, 1.7 GB
	 *   Micro Instance: 0.5 EC2 Compute Unit, 0.633 GB
	 *   We decrease the memory size two times to enable oversubscription
	 *
	 * New Vm instance types:
	 *  INSTANCES TAKEN ON M4 TAB BECAUSE THEY USE A PROCESSOR THAT HAVE A SIMILAR IN SPEC BENCHMARK
	 * 	 High-Memory Extra Large Instance
	 * 	 High-Cpu Medium Instance
	 *   Large Instance
	 *   Extra Large Instance
	 *   NAME   | VCpu | Mem |
	 * c5.xlarge|  2   |  32 | - Mem - original is 4VCpu but use 2 instead(too much cpu)
	 * c5.xlarge|  4   |  8  | - CPU 
	 * m4.large |  2   |  8  | - Normal
	 * m4.xlarge|  4   |  16 | - Normal
	 */
	public final static int VM_TYPES	= 4;
	public final static int[] VM_MIPS	= { 4000, 3000, 2000, 2000 };
	public final static int[] VM_PES	= { 1, 2, 1, 2 };
	public final static int[] VM_RAM	= { 32768,  8192, 8192, 16384};
	public final static int VM_BW		= 100000; // 100 Mbit/s
	public final static int VM_SIZE		= 2500; // 2.5 GB

	/*
	 * 	HOST TYPES:
	 *   Cálculos baseados na ProLiant ML110 G4 - 2 PE - cada 1860 MIPS
	 *   link: https://www.spec.org/power_ssj2008/results/res2011q1/power_ssj2008-20110124-00338.html
	 *   Máquina nova: Hewlett Packard Enterprise ProLiant DL325 Gen10(AMD EPYC 7551P 2.0 GHz)
	 *   RAM: 131072MB
	 *   link:https://www.spec.org/power_ssj2008/results/res2018q3/power_ssj2008-20180619-00821.html
	 *   3720 MIPS - 54.479     ssj_ops
	 *   x		   - 2.212.736 ssj_ops
	 *   x = 151092 MIPS/32 cores = 4721 MIPS por Core
	 *   
	 *   
	 *   Cálculos baseados na ProLiant ML110 G4 - 2 PE - cada 1860 MIPS
	 *   link: https://www.spec.org/power_ssj2008/results/res2011q1/power_ssj2008-20110124-00338.html
	 *   Máquina nova: Hewlett Packard Enterprise ProLiant DL385 Gen10(AMD EPYC 7601 2.20 GHz)
	 *   RAM: 131072MB
	 *   link:https://www.spec.org/power_ssj2008/results/res2018q3/power_ssj2008-20180619-00821.html
	 *   3720 MIPS - 54.479     ssj_ops
	 *   x		   - 4.283.406 ssj_ops
	 *   x = 292484 MIPS/64 cores = 4570 MIPS por Core
	 *   
	 *   
	 */
	public final static int HOST_TYPES	 = 2;
	public final static int[] HOST_MIPS	 = { 4721, 4570};
	public final static int[] HOST_PES	 = { 32, 64 };
	public final static int[] HOST_RAM	 = { 131072, 131072 }; 
	public final static int HOST_BW		 = 5000000; // 5 Gbit/s
	public final static int HOST_STORAGE = 10000000; // 10 TB

	public final static PowerModel[] HOST_POWER = {
		new PowerModelSpecPowerHewlettPackardEnterpriseProLiantDL325(),
		new PowerModelSpecPowerHewlettPackardEnterpriseProLiantDL385()
	};

}
