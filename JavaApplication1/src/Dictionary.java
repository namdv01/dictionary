
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.TimerTask;
import java.util.stream.Stream;
import javax.swing.JOptionPane;
//import javax.swing.Timer;
import java.util.Timer;



/**
 *
 * @author Admin
 */
public class Dictionary extends javax.swing.JFrame {
    private String[] s;
    private String[] sve;
    private String[] st;
    private String[] sev;
    private String t;
    private int n = 0;
    private int nst = 0;
    private final int[][] a = new int[350000][97];
    private int top=0;
    private String word = "";
    private String sayMe;
    private String stry = "";
    private int[] atry = new int[15];
    private int ntry = 0;
    //Voice voice = new Voice("kevin16");
    Timer timer = new Timer();
    private String old = "";
    
    public void addE_V(int x){
        String v = s[x];
        int ns = v.length();
        int i = 1;
        int d;
        int vt = 0;
        while(i<ns && v.charAt(i)!=' '){
            sev[x] = sev[x]+v.charAt(i);
            d = (int) v.charAt(i) - 31;
            if(d<0||d>95) d=96;
            if( a[vt][d] == 0) {
                top++;
                a[vt][d]=top;
                vt=top;
            } else vt=a[vt][d];
            i++;
        }
        if(i==ns||(i<ns-1 && v.charAt(i+1) == '/')) a[vt][0]=x;
    }
    
    public int searchE_V(String x){
        x = x.toLowerCase();
        x.trim();
        int ns = x.length();
        int vt=0;
        int d;
        for(int i=0;i<ns;i++){
            d = (int) x.charAt(i) -31;
            if(d<0||d>95) d=96;
            vt=a[vt][d];
            if(vt==0) return 0;
        }
        return vt;        
    }
    
    public void nhapE_V(){
    s = new String[110000];
    sev = new String[110000];
    sev[1]="";
    String fileName = "D:\\data\\1.txt";
        try(Stream<String> stream = Files.lines(Paths.get(fileName),StandardCharsets.UTF_8)){//đưa về dạng chuẩn utf8
            stream.forEach(line ->{
		t = line;
                if(t.length()>0){
                if(t.charAt(0) == '@'){ 
                    n++;
                    s[n]="";
                    sev[n+1]="";}
                }
                if(s[n]=="") {
                    s[n]=t;
                    addE_V(n);
                } 
                else s[n]=s[n]+'\n'+t;
            });
		} catch (Exception e) {
			e.printStackTrace();
		}              
   }
    
    public void dequyE_V(int u){
        if(a[u][0]!=0) jTextArea2.append(sev[a[u][0]]+'\n');
        for(int i=1;i<97;i++)
            if(a[u][i]!=0) dequyE_V(a[u][i]);
    }
    
    public void tryE_V(int vt,int u){
        if(u == ntry) dequyE_V(vt);
        else
        for(int i=0;i<ntry;i++)
            if(atry[i]==0){
                int d = (int) stry.charAt(i) -31;
                if(d<0||d>95) d=96;
                if(a[vt][d]!=0){
                    atry[i]=1;
                    tryE_V(a[vt][d],u+1);
                    atry[i]=0;
                }
            }
    }
    
    public void sugE_V(String sug){
        sug = sug.toLowerCase();
        sug.trim();
        int ns = sug.length();
        int vt=0;
        int d;
        for(int i=0;i<ns;i++){
            d = (int) sug.charAt(i) -31;
            if(d<0||d>95) d=96;
            vt=a[vt][d];
            if(vt==0) break;
        }
        if(vt!=0){
            dequyE_V(vt);
        } else {
            ntry = sug.length();
            stry = sug;
            tryE_V(0,0);
        }
    }
    
    public void addV_E(int x){
        String v = sve[x];
        int nv = v.length();
        int i = 0;
        while(i<nv && v.charAt(i)!=':') i++;
        i--;
        st[x]="";
        for(int j=0;j<i;j++) st[x] = st[x] + v.charAt(j);
    }
    
    public void sortV_E(int left,int right){
        int l = left;
        int r = right;
        String k = st[(l+r)/2];
        while(l<=r){
            while(l<=r && st[l].compareTo(k)<0)l++;
            while(r>=l && st[r].compareTo(k)>0)r--;
            if(l<=r){
                String tm = st[l];
                st[l] = st[r];
                st[r] = tm;
                tm = sve[l];
                sve[l] = sve[r];
                sve[r] = tm;
                l++; r--;
            }
        }
        if(l<right)sortV_E(l,right);
        if(r>left)sortV_E(left,r);
    }
    
    public void nhapV_E(){
    sve = new String[550000];
    st = new String[550000]; st[0]="";
    String fileName = "D:\\data\\2.txt";
        try(Stream<String> stream = Files.lines(Paths.get(fileName),StandardCharsets.UTF_8)){//đưa về dạng chuẩn utf8
            stream.forEach(line ->{
		t = line;
                nst++;
                sve[nst]="";
                sve[nst]=t;
                addV_E(nst);
            });
		} catch (Exception e) {
			e.printStackTrace();
		}
        sortV_E(1,nst);
    }
    
    public int search(String x){
        int l = 1;
        int r = nst;
        int mid;
        while(l <= r){
            mid = (l+r) / 2 ;
            if(st[mid].compareTo(x) == 0)return mid;
            if(st[mid].compareTo(x) > 0) r = mid - 1;
            if(st[mid].compareTo(x) < 0) l = mid + 1;
            }
        return 0;
    }
    
    public int searchV_E(String x){
        x = x.toLowerCase();
        int nx = x.length();
        int kt = search(x);
        if (kt != 0) return kt;
        String v ;
        char c = Character.toUpperCase(x.charAt(0));
        v = ""; v = v + c;
        for(int i=1;i<nx;i++) v = v + x.charAt(i);
        kt = search(v);
        if (kt != 0) return kt;
        x = ""; x = x+c;
        for(int i=1;i<nx;i++)
            if(v.charAt(i-1)!=' ') x = x + v.charAt(i);
            else {
                c = Character.toUpperCase(v.charAt(i));
                x = x + c;
            }
        kt = search(x);
        if (kt != 0) return kt;
        return 0;
    }
    
    public void sugV_E(String sug){
        jTextArea2.setText("");
        int l = 1;
        int r = nst;
        int mid = 0;
        int kt = 1;
        int k = sug.length();
        while(k>0&&sug.charAt(k-1)==' ')k--;
        while(l <= r){
            mid = (l+r) / 2 ;
            if(st[mid].compareTo(sug) == 0) break;
            if(st[mid].compareTo(sug) > 0) r = mid - 1;
            if(st[mid].compareTo(sug) < 0) l = mid + 1;
            }
        for(int i = mid;i<mid+1000;i++){
            if(kt==0||mid>nst)break;
            if(st[i].length()>=k){
            for(int j=0;j<k;j++)
                if(sug.charAt(j)!=st[i].charAt(j)){
                    kt = 0;
                    break;
                }
            if (kt==1) jTextArea2.append(st[i]+'\n');
            }
        }
    }
    
    /**
     * Creates new form Dictionary
     */
    public Dictionary() {
        this.nhapE_V();
        this.nhapV_E();
        this.setBounds(300,120, 800, 550);
        initComponents();
        jFrame3.setBounds(500,230, 430, 280);
        jFrame1.setBounds(500,230, 430, 280);
        jFrame2.setBounds(500,230, 430, 320);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jFrame3 = new javax.swing.JFrame();
        jLabel7 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jFrame1 = new javax.swing.JFrame();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jFrame2 = new javax.swing.JFrame();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        jFrame3.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel7.setFont(new java.awt.Font("Tahoma", 3, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 255));
        jLabel7.setText("Add Word");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane3.setViewportView(jTextArea3);

        jLabel8.setText("Impost");

        jLabel9.setText("Translate");

        jButton6.setText("ADD");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("CANCEL");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jFrame3Layout = new javax.swing.GroupLayout(jFrame3.getContentPane());
        jFrame3.getContentPane().setLayout(jFrame3Layout);
        jFrame3Layout.setHorizontalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrame3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGroup(jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jFrame3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jFrame3Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jFrame3Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
        );
        jFrame3Layout.setVerticalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jFrame3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6)
                        .addGap(18, 18, 18)
                        .addComponent(jButton7)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jFrame3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addContainerGap(65, Short.MAX_VALUE))))
        );

        jFrame1.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel10.setFont(new java.awt.Font("Tahoma", 3, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 255));
        jLabel10.setText("Delete Word");

        jLabel11.setText("Impost");

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jButton5.setText("DELETE");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton8.setText("CANCEL");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame1Layout.createSequentialGroup()
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jFrame1Layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(jButton5)
                        .addGap(69, 69, 69)
                        .addComponent(jButton8))
                    .addGroup(jFrame1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrame1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)))
                .addGap(0, 59, Short.MAX_VALUE))
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(31, 31, 31)
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton8))
                .addContainerGap(144, Short.MAX_VALUE))
        );

        jFrame2.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel12.setFont(new java.awt.Font("Tahoma", 3, 36)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 255));
        jLabel12.setText("Change Word");

        jLabel13.setText("Word need change");

        jLabel14.setText("Translation again");

        jTextArea4.setColumns(20);
        jTextArea4.setRows(5);
        jScrollPane4.setViewportView(jTextArea4);

        jButton9.setText("CHANGE");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("CANCEL");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame2Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jFrame2Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jFrame2Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jFrame2Layout.createSequentialGroup()
                        .addGroup(jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                            .addGroup(jFrame2Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(11, 11, 11))))
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame2Layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jFrame2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton9)
                        .addGap(29, 29, 29)
                        .addComponent(jButton10)
                        .addGap(33, 33, 33))
                    .addGroup(jFrame2Layout.createSequentialGroup()
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 255));
        setIconImages(null);
        addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                formComponentAdded(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon("D:\\data\\voice.png")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("Dictionary");
        jLabel1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                jLabel1AncestorMoved(evt);
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabel1AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jTextField1.setToolTipText("");
        jTextField1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jTextField1AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField1MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField1MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextField1MousePressed(evt);
            }
        });
        jTextField1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jTextField1InputMethodTextChanged(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTextField1PropertyChange(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton2.setIcon(new javax.swing.ImageIcon("D:\\data\\add.png")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon("D:\\data\\delete.png")); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon("D:\\data\\change.png")); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel4.setText("Translate");

        jLabel6.setText("Selection");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Anh - Việt", "Việt - Anh" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Import");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jTextArea2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextArea2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTextArea2);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(123, 123, 123)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 201, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                    .addComponent(jLabel2))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2)))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(word==""){
            if(jComboBox2.getSelectedIndex()==0){
            JOptionPane.showMessageDialog(null,"Please enter words to pronounce!","Warning",JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,"Vui lòng nhập từ để phát âm!","Cảnh báo",JOptionPane.INFORMATION_MESSAGE);
        }
        } else {
            //voice.say(sayMe);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel1AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1AncestorAdded

    private void jLabel1AncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel1AncestorMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1AncestorMoved

    private void formComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_formComponentAdded

    }//GEN-LAST:event_formComponentAdded

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        jTextField1.setText("");
        jTextArea1.setText("");
        jTextArea2.setText("");
        timer.purge();
        if(jComboBox2.getSelectedIndex()==0){
            jLabel1.setText("Dictionary");
            jLabel2.setText("Import");
            jLabel4.setText("Translate");
            jLabel6.setText(" Selection");
            jLabel7.setText("Add Word");
            jLabel8.setText("Impost");
            jLabel9.setText("Translate");
            jLabel12.setText("Change Word");
            jLabel13.setText("Word need change");
            jLabel14.setText("Translation again");
            jLabel10.setText("Delete Word");
            jLabel11.setText("Impost");
            jButton6.setText("ADD");
            jButton7.setText("CANCEL");
            jButton9.setText("CHANGE");
            jButton10.setText("CANCEL");
            jButton5.setText("DELETE");
            jButton8.setText("CANCEL");
        } else {
            jLabel1.setText("   Từ Điển ");
            jLabel2.setText("Nhập");
            jLabel4.setText("Dịch Nghĩa");
            jLabel6.setText("Lựa Chọn");
            jLabel7.setText("Thêm từ");
            jLabel8.setText("Nhập từ");
            jLabel9.setText("Dịch nghĩa");
            jLabel10.setText("     Xóa Từ");
            jLabel11.setText("Nhập");
            jLabel12.setText("     Sửa Từ");
            jLabel13.setText("Nhập từ cần sửa");
            jLabel14.setText("Dịch nghĩa lại");
            jButton6.setText("Thêm");
            jButton7.setText("Hủy");
            jButton9.setText("Sửa");
            jButton10.setText("Hủy");
            jButton5.setText("Xóa");
            jButton8.setText("Hủy");
        }
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       jFrame3.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       jFrame1.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       jFrame2.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        String kt="";
        if(kt.compareTo(jTextField2.getText()) == 0) {
            if(jComboBox2.getSelectedIndex()==0) JOptionPane.showMessageDialog(null,"Please enter the word to add!","Warning",JOptionPane.INFORMATION_MESSAGE);
            else JOptionPane.showMessageDialog(null,"Vui lòng nhập từ cần thêm!","Cảnh báo",JOptionPane.INFORMATION_MESSAGE);
        }
        else{ 
        if (kt.compareTo(jTextArea3.getText()) == 0){
            if(jComboBox2.getSelectedIndex()==0) JOptionPane.showMessageDialog(null,"Please enter translation!","Warning",JOptionPane.INFORMATION_MESSAGE);
            else JOptionPane.showMessageDialog(null,"Vui lòng nhập dịch nghĩa!","Cảnh báo",JOptionPane.INFORMATION_MESSAGE);
        }   else {
            jFrame3.setVisible(false);
            String x = jTextField2.getText();
            String y = jTextArea3.getText();
            jTextField2.setText("");
            jTextArea3.setText("");
            if(jComboBox2.getSelectedIndex()==0){
                int u = searchE_V(x);
                u = a[u][0];
                if (u==0) {
                   n++;
                   s[n]='@'+x;
                   addE_V(n);
                   s[n]=s[n]+'\n'+y;
                   JOptionPane.showMessageDialog(null,"Add '"+x+"' successfully!","Notification",JOptionPane.INFORMATION_MESSAGE); 
                } else
                {
                    JOptionPane.showMessageDialog(null,"'"+x+"' is already in the dictionary!","Warning",JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                int u = searchV_E(x);
                if(u == 0){
                    nst++;
                    st[nst]=x;
                    sve[nst]=x+" : "+y;
                    sortV_E(1,nst);
                    JOptionPane.showMessageDialog(null,"Thêm '"+x+"' thành công!","Thông báo",JOptionPane.INFORMATION_MESSAGE); 
                }else
                {
                   JOptionPane.showMessageDialog(null,"'"+x+"' đã có trong từ điển!","Cảnh báo",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        jFrame3.setVisible(false);
        jTextField2.setText("");
        jTextArea3.setText("");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String kt="";
        if(kt.compareTo(jTextField3.getText()) == 0) {
            if(jComboBox2.getSelectedIndex()==0) JOptionPane.showMessageDialog(null,"Please enter the word to delete!","Cảnh báo",JOptionPane.INFORMATION_MESSAGE);
            else JOptionPane.showMessageDialog(null,"Vui lòng nhập từ cần xóa!","Cảnh báo",JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            jFrame1.setVisible(false);
            String x = jTextField3.getText();
            jTextField3.setText("");
            if(jComboBox2.getSelectedIndex()==0){
                int u = searchE_V(x);
                if (u==0) {
                   JOptionPane.showMessageDialog(null,"'"+x+"' not in the dictionary!","Warning",JOptionPane.INFORMATION_MESSAGE); 
                } else
                {
                    s[a[u][0]]="";
                    a[u][0]=0;
                    JOptionPane.showMessageDialog(null,"Deleted '"+x+"' out of the dictionary!","Notification",JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                int u = searchV_E(x);
                if(u == 0){
                    JOptionPane.showMessageDialog(null,"'"+x+"' không có trong từ điển!","Cảnh báo",JOptionPane.INFORMATION_MESSAGE); 
                }else
                {
                    String tm = st[u];
                    st[u] = st[nst];
                    st[nst] = tm;
                    tm = sve[u];
                    sve[u] = sve[nst];
                    sve[nst] = tm;
                    nst--;
                    sortV_E(1,nst);
                    JOptionPane.showMessageDialog(null,"Đã xóa '"+x+"' ra khỏi từ điển!","Thông báo",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        jFrame1.setVisible(false);
        jTextField3.setText("");
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        String kt="";
        if(kt.compareTo(jTextField4.getText()) == 0) {
            if(jComboBox2.getSelectedIndex()==0) JOptionPane.showMessageDialog(null,"Please enter the word to change!","Warning",JOptionPane.INFORMATION_MESSAGE);
            else JOptionPane.showMessageDialog(null,"Vui lòng nhập từ cần sửa!","Cảnh báo",JOptionPane.INFORMATION_MESSAGE);
        }
        else{ 
        if (kt.compareTo(jTextArea4.getText()) == 0){
            if(jComboBox2.getSelectedIndex()==0) JOptionPane.showMessageDialog(null,"Please enter translation again!","Warning",JOptionPane.INFORMATION_MESSAGE);
            else JOptionPane.showMessageDialog(null,"Vui lòng nhập dịch nghĩa lại!","Cảnh báo",JOptionPane.INFORMATION_MESSAGE);
        }   else {
            jFrame2.setVisible(false);
            String x = jTextField4.getText();
            String y = jTextArea4.getText();
            jTextField4.setText("");
            jTextArea4.setText("");
            if(jComboBox2.getSelectedIndex()==0){
                int u = searchE_V(x);
                if (u==0) {
                   JOptionPane.showMessageDialog(null,"'"+x+"' not in the dictionary!","Warning",JOptionPane.INFORMATION_MESSAGE); 
                } else
                {
                    u = a[u][0];
                    s[u]='@'+x+'\n'+y;
                    JOptionPane.showMessageDialog(null,"Changed '"+x+"' successfully!","Notification",JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                int u = searchV_E(x);
                if(u == 0){
                    JOptionPane.showMessageDialog(null,"'"+x+"' không có trong từ điển!","Cảnh báo",JOptionPane.INFORMATION_MESSAGE); 
                }else
                {
                    sve[u]=x+" : "+y;
                   JOptionPane.showMessageDialog(null,"'"+x+"' đã sửa thành công!","Thông báo",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        jFrame2.setVisible(false);
        jTextField4.setText("");
        jTextArea4.setText("");
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        String kt="";
        if(kt.compareTo(jTextField3.getText()) == 0) {
            if(jComboBox2.getSelectedIndex()==0) JOptionPane.showMessageDialog(null,"Please enter the word to delete!","Warning",JOptionPane.INFORMATION_MESSAGE);
            else JOptionPane.showMessageDialog(null,"Vui lòng nhập từ cần xóa!","Cảnh báo",JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            jFrame1.setVisible(false);
            String x = jTextField3.getText();
            jTextField3.setText("");
            if(jComboBox2.getSelectedIndex()==0){
                int u = searchE_V(x);
                if (u==0) {
                   JOptionPane.showMessageDialog(null,"'"+x+"' not in the dictionary!","Warning",JOptionPane.INFORMATION_MESSAGE); 
                } else
                {
                    s[a[u][0]]="";
                    a[u][0]=0;
                    JOptionPane.showMessageDialog(null,"Deleted '"+x+"' out of the dictionary!","Notification",JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                int u = searchV_E(x);
                if(u == 0){
                    JOptionPane.showMessageDialog(null,"'"+x+"' không có trong từ điển!","Cảnh báo",JOptionPane.INFORMATION_MESSAGE); 
                }else
                {
                    String tm = st[u];
                    st[u] = st[nst];
                    st[nst] = tm;
                    tm = sve[u];
                    sve[u] = sve[nst];
                    sve[nst] = tm;
                    nst--;
                    JOptionPane.showMessageDialog(null,"Đã xóa '"+x+"' ra khỏi từ điển!","Thông báo",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        String kt="";
        if(kt.compareTo(jTextField2.getText()) == 0) {
            if(jComboBox2.getSelectedIndex()==0) JOptionPane.showMessageDialog(null,"Please enter the word to add!","Warning",JOptionPane.INFORMATION_MESSAGE);
            else JOptionPane.showMessageDialog(null,"Vui lòng nhập từ cần thêm!","Cảnh báo",JOptionPane.INFORMATION_MESSAGE);
        }
        else{ 
        if (kt.compareTo(jTextArea3.getText()) == 0){
            if(jComboBox2.getSelectedIndex()==0) JOptionPane.showMessageDialog(null,"Please enter translation!","Warning",JOptionPane.INFORMATION_MESSAGE);
            else JOptionPane.showMessageDialog(null,"Vui lòng nhập dịch nghĩa!","Cảnh báo",JOptionPane.INFORMATION_MESSAGE);
        }   else {
            jFrame3.setVisible(false);
            String x = jTextField2.getText();
            String y = jTextArea3.getText();
            jTextField2.setText("");
            jTextArea3.setText("");
            if(jComboBox2.getSelectedIndex()==0){
                int u = searchE_V(x);
                if (u==0) {
                   n++;
                   s[n]='@'+x;
                   addE_V(n);
                   s[n]=s[n]+'\n'+y;
                   JOptionPane.showMessageDialog(null,"Add '"+x+"' successfully!","Warning",JOptionPane.INFORMATION_MESSAGE); 
                } else
                {
                    JOptionPane.showMessageDialog(null,"'"+x+"' is already in the dictionary!","Warning",JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                int u = searchV_E(x);
                if(u == 0){
                    nst++;
                    st[nst]=x;
                    sve[nst]=x+" : "+y;
                    sortV_E(1,nst);
                    JOptionPane.showMessageDialog(null,"Thêm '"+x+"' thành công!","Thông báo",JOptionPane.INFORMATION_MESSAGE); 
                }else
                {
                   JOptionPane.showMessageDialog(null,"'"+x+"' đã có trong từ điển!","Cảnh báo",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        }
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        if(jComboBox2.getSelectedIndex()==0){
            word = jTextField1.getText();
            //jPanel1.add(word);
            jTextField1.setText("");
            int u = searchE_V(word);
            u = a[u][0];
            if(u==0) JOptionPane.showMessageDialog(null,"The search word is not in the dictionary!","Warning",JOptionPane.INFORMATION_MESSAGE);
            else {
                sayMe = word;
                jTextArea1.setText(s[u]);
            }
            jTextArea1.setCaretPosition(0);
        }
        else {
            word = jTextField1.getText();
            jTextField1.setText("");
            int u = searchV_E(word);
            if(u==0) JOptionPane.showMessageDialog(null,"Từ tìm kiếm không có trong từ điển!","Cảnh báo",JOptionPane.INFORMATION_MESSAGE);
            else {
                sayMe = word;
                jTextArea1.setText(sve[u]);
            }
            jTextArea1.setCaretPosition(0);
        }
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTextField1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jTextField1AncestorAdded
        
    }//GEN-LAST:event_jTextField1AncestorAdded

    private void jTextField1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTextField1PropertyChange
        
    }//GEN-LAST:event_jTextField1PropertyChange

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTextField1InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTextField1InputMethodTextChanged
        
    }//GEN-LAST:event_jTextField1InputMethodTextChanged

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
                public void run() {
                    if(old.compareTo(jTextField1.getText())!=0){
                        old = jTextField1.getText();
                        if(jComboBox2.getSelectedIndex()==0){
                            jTextArea2.setText("");
                            sugE_V(old);
                        } else {
                        sugV_E(old);    
                        }
                    }
                }
            }, 0, 100);
        
    }//GEN-LAST:event_jTextField1MouseClicked

    private void jTextField1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseExited

    }//GEN-LAST:event_jTextField1MouseExited

    private void jTextField1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MousePressed

    }//GEN-LAST:event_jTextField1MousePressed

    private void jTextArea2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea2MouseClicked

    }//GEN-LAST:event_jTextArea2MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dictionary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dictionary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dictionary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dictionary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dictionary().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JFrame jFrame3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables

}
