package compacta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;

import roundRobin.CircularHostList;

/**
 * Round-Robin {@link Vm} allocation policy.
 * 
 * @author alessandro
 */
public class CompactVmAllocationPolicy extends org.cloudbus.cloudsim.VmAllocationPolicy {
	
	private final Map<String, Host> vm_table = new HashMap<String, Host>();
	
	private final CircularHostList hosts;
	
	public CompactVmAllocationPolicy(List<? extends Host> list) {
		super(list);
		this.hosts = new CircularHostList(list);
	}

	@Override
	public boolean allocateHostForVm(Vm vm) {
		if (this.vm_table.containsKey(vm.getUid())) {
			return true;
		}
		int i = 0;
		boolean vm_allocated;
		vm_allocated = this.allocateHostForVm(vm, this.hosts.actual());
		while(!vm_allocated && i <= this.hosts.size()) {
			this.hosts.inc_Ini();
			i++;
			vm_allocated = this.allocateHostForVm(vm, this.hosts.actual());
		};

		return vm_allocated;
	}

	@Override
	public boolean allocateHostForVm(Vm vm, Host host) 
	{
		if (host != null && host.vmCreate(vm)) 
		{
			vm_table.put(vm.getUid(), host);
			Log.formatLine("%f: VM #" + vm.getId() + " has been allocated to the host#" + host.getId() + 
					" datacenter #" + host.getDatacenter().getId() + "(" + host.getDatacenter().getName() + ") #", 
					CloudSim.clock());
			return true;
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> optimizeAllocation(List<? extends Vm> vmList) {
		return null;
	}

	@Override
	public void deallocateHostForVm(Vm vm) {
		Host host = this.vm_table.remove(vm.getUid());

		if (host != null) {
			host.vmDestroy(vm);
		}
	}

	@Override
	public Host getHost(Vm vm) {
		return this.vm_table.get(vm.getUid());
	}

	@Override
	public Host getHost(int vmId, int userId) {
		return this.vm_table.get(Vm.getUid(userId, vmId));
	}
}