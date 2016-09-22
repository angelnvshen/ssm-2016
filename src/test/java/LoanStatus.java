import java.util.Collections;

public enum LoanStatus {
	All("所有数据", 10),
	AllSettlement("所有结清", 9),
	AllOverdue("所有逾期", 8),
	EarlySettlement("提前结清", 1), 
	NaturalSettlement("自然结清 ", 2), 
	TripartiteSettlement("三方结清", 3), 
	LitigationSettlement("诉讼结清", 4),
	Overdue("逾期", 5),
	Pending("待处理", 6),
	BadDebt("坏账", 7);
	// 成员变量
    private String name;
    private int index;

    // 构造方法
    private LoanStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }
 // 普通方法
    public static String getName(int index) {
        for (LoanStatus c : LoanStatus.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
    public static void main(String[] args) {

        System.out.println(LoanStatus.AllOverdue.getIndex());
        System.out.println("8".equals(LoanStatus.AllOverdue.getIndex()+""));

    	System.out.println(LoanStatus.All.index);
    	LoanStatus [] ar = LoanStatus.values();
    	for(LoanStatus a : ar)
    		System.out.println(a.index +" -  " + a.name);
	}
    
}
