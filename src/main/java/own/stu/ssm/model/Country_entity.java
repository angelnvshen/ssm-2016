package own.stu.ssm.model;

import java.util.List;
import java.util.concurrent.locks.Condition;

/**
 * Created by CHANEL on 2016/10/6.
 */
public class Country_entity {
    private List<Country> countryList;
    private String resultFlag;
    private String message;

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(String resultFlag) {
        this.resultFlag = resultFlag;
    }
}
