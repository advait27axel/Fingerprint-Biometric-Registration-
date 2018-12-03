package biometric.attendance;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.ui.swing.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class EnrollmentDialog
	extends JDialog
{
    public EnumMap<DPFPFingerIndex, DPFPTemplate> templates;
    
     public static ArrayList<byte[]> fingerTemplates = new ArrayList<>(); 
     public byte[]  tests = new byte [10] ;
     public byte [] Finger1  ;
     public byte[] Finger2 ;
     
     
   

    public EnrollmentDialog(Frame owner, int maxCount, final String reasonToFail, EnumMap<DPFPFingerIndex, DPFPTemplate> templates) {
        super (owner, true);
        this.templates = templates;

        setTitle("Fingerprint Enrollment");
 
        DPFPEnrollmentControl enrollmentControl = new DPFPEnrollmentControl();

        EnumSet<DPFPFingerIndex> fingers = EnumSet.noneOf(DPFPFingerIndex.class);
        fingers.addAll(templates.keySet());
        enrollmentControl.setEnrolledFingers(fingers);
        enrollmentControl.setMaxEnrollFingerCount(maxCount);

        enrollmentControl.addEnrollmentListener(new DPFPEnrollmentListener()
        {
            public void fingerDeleted(DPFPEnrollmentEvent e) throws DPFPEnrollmentVetoException {
                if (reasonToFail != null) {
                    throw new DPFPEnrollmentVetoException(reasonToFail);
                } else {
                    EnrollmentDialog.this.templates.remove(e.getFingerIndex());
                }
            }

            public void fingerEnrolled(DPFPEnrollmentEvent e) throws DPFPEnrollmentVetoException {
                    if (reasonToFail != null) {
    //                  e.setStopCapture(false);
                        throw new DPFPEnrollmentVetoException(reasonToFail);
                    } else
                         EnrollmentDialog.this.templates.put(e.getFingerIndex(), e.getTemplate());


                    if(Finger1 == null){
                        Finger1 = e.getTemplate().serialize();
                        fingerTemplates.add(Finger1);
                    }else {
                        Finger2 =e.getTemplate().serialize();
                        fingerTemplates.add(Finger2);
                    }
                     System.out.print(fingerTemplates.size());


                }
            });

	getContentPane().setLayout(new BorderLayout());

	JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);//End Dialog
            }
        });

        JPanel bottom = new JPanel();
        bottom.add(closeButton);
        add(enrollmentControl, BorderLayout.CENTER);
        add(bottom, BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);         
   }
    
    

    
}