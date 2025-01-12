package Business.Network;

import java.util.ArrayList;

public class NetworkDirectory {
    private ArrayList<Network> networkList;
    
    public NetworkDirectory() {
        networkList = new ArrayList<Network>();
    }

    public ArrayList<Network> getNetworkList() {
        return networkList;
    }
    
    public Network createAndAddNetwork() {
        Network network = new Network();
        networkList.add(network);
        return network;
    }
    
    public void removeNetwork(Network network) {
        networkList.remove(network);
    }
}