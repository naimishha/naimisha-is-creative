package UserInterface.AdminRole;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import javax.swing.*;
import java.awt.*;

public class ManageEnterpriseAdminJPanel extends JPanel {
    private JPanel container;
    private Enterprise enterprise;
    
    public ManageEnterpriseAdminJPanel(JPanel container, Enterprise enterprise) {
        this.container = container;
        this.enterprise = enterprise;
        initComponents();
    }
    
    private void initComponents() {
        // Add enterprise admin management components here
    }
} 