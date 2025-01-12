package Business.Network;

import Business.Enterprise.EnterpriseDirectory;

public class Network {
    private String name;
    private EnterpriseDirectory enterpriseDirectory;
    
    public Network() {
        enterpriseDirectory = new EnterpriseDirectory();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnterpriseDirectory getEnterpriseDirectory() {
        return enterpriseDirectory;
    }
    
    @Override
    public String toString() {
        return name;
    }
}