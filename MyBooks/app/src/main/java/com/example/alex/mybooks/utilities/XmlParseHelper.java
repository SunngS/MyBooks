package com.example.alex.mybooks.utilities;

import android.util.Xml;

import com.example.alex.mybooks.entities.LoginResult;
import com.example.alex.mybooks.entities.User;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by v0cn115 on 2015/1/23.
 */
public class XmlParseHelper {


    public LoginResult ParseLoginResultXml(InputStream stream) throws IOException, XmlPullParserException {
        LoginResult result = null;
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
            parser.setInput(stream,"UTF-8");
            parser.nextTag();
            parser.require(XmlPullParser.START_TAG,null,"Login");
            result = new LoginResult();
            while (parser.next() != XmlPullParser.END_TAG){
                if (parser.getEventType() != XmlPullParser.START_TAG){
                    continue;
                }
                String tagName = parser.getName();
                if (tagName.equals("ResultCode"))
                {
                    String resultCode = this.GetInnerTextOfXmlElement(parser);
                    switch (resultCode){
                        case LoginResult.LOGIN_SUCCEED:
                            result.setSucceed(true);
                            result.setResultCode(resultCode);
                            break;
                        case LoginResult.USER_NOT_EXISTS:
                            result.setSucceed(false);
                            result.setResultCode(resultCode);
                            break;
                        case LoginResult.PASSWORD_NOT_CORRECT:
                            result.setSucceed(false);
                            result.setResultCode(resultCode);
                            break;
                        default:
                            result.setSucceed(false);
                            result.setResultCode(LoginResult.DEFAULT_RESULT_CODE);
                    }
                    if (result.getSucceed() == false){
                        break;
                    }
                }
                else if (tagName.equals("User")){
                    User loginUser = ParseUserEntityFromXml(parser);
                    result.setLoginUser(loginUser);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            result = null;
        }
        finally {
            stream.close();
        }


        return result;
    }

    private User ParseUserEntityFromXml(XmlPullParser parser) throws IOException, XmlPullParserException {
        User loginUser = new User();
        parser.require(XmlPullParser.START_TAG,null,"User");
        while (parser.next() != XmlPullParser.END_TAG){
            if (parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String tagName = parser.getName();
            if (tagName.equals("UserName")){
                loginUser.setUsername(GetInnerTextOfXmlElement(parser));
            }
            else if (tagName.equals("State")){
                loginUser.setState(GetInnerTextOfXmlElement(parser));
            }
        }
        return loginUser;
    }

    private String GetInnerTextOfXmlElement(XmlPullParser parser) throws IOException, XmlPullParserException {
        String text = null;
        if (parser.next() == XmlPullParser.TEXT){
            text = parser.getText();
            parser.nextTag();
        }
        return text;
    }


}
