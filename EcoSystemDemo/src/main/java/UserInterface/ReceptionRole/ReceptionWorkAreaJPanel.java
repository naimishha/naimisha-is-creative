package UserInterface.ReceptionRole;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;

public class ReceptionWorkAreaJPanel extends JPanel {
    private JPanel container;
    private UserAccount userAccount;
    private Organization organization;
    private Enterprise enterprise;
    private EcoSystem system;
    
    public ReceptionWorkAreaJPanel(JPanel container, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem system) {
        this.container = container;
        this.userAccount = account;
        this.organization = organization;
        this.enterprise = enterprise;
        this.system = system;
        initComponents();
    }
    
    private void initComponents() {
        // Add your UI components initialization here
    }
} 