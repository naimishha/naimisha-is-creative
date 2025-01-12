package UserInterface.AdminRole;

import Business.EcoSystem;
import Business.Network.Network;
import javax.swing.*;
import java.awt.*;

public class ManageEnterpriseJPanel extends JPanel {
    private JPanel container;
    private EcoSystem system;
    
    public ManageEnterpriseJPanel(JPanel container, EcoSystem system) {
        this.container = container;
        this.system = system;
        initComponents();
    }
    
    private void initComponents() {
        // Add enterprise management components here
    }
} 