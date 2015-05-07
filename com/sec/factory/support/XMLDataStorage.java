package com.sec.factory.support;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.SystemProperties;
import android.util.Base64;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLDataStorage
{
  private static XMLDataStorage mInstance = null;
  private DocumentBuilder mDOMParser;
  private Document mDocument;
  private boolean mWasCompletedParsing = false;
  private XPath mXPath;
  
  private XMLDataStorage()
  {
    try
    {
      DocumentBuilderFactory localDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
      localDocumentBuilderFactory.setIgnoringComments(true);
      localDocumentBuilderFactory.setIgnoringElementContentWhitespace(true);
      this.mDOMParser = localDocumentBuilderFactory.newDocumentBuilder();
      this.mXPath = XPathFactory.newInstance().newXPath();
      return;
    }
    catch (ParserConfigurationException localParserConfigurationException)
    {
      FtUtil.log_e(localParserConfigurationException);
    }
  }
  
  private Node cloneNode(Document paramDocument, Element paramElement)
  {
    Element localElement = paramDocument.createElement(paramElement.getNodeName());
    if (paramElement.hasAttributes()) {
      for (String str : getAttributeNameSet(paramElement)) {
        localElement.setAttribute(str, paramElement.getAttribute(str));
      }
    }
    NodeList localNodeList = paramElement.getChildNodes();
    for (int i = 0; i < localNodeList.getLength(); i++) {
      if (localNodeList.item(i).getNodeType() == 1) {
        localElement.appendChild(cloneNode(paramDocument, (Element)localNodeList.item(i)));
      }
    }
    return localElement;
  }
  
  private InputStream convertBytesToIS(byte[] paramArrayOfByte)
  {
    return new ByteArrayInputStream(paramArrayOfByte);
  }
  
  private String convertIStoString(Context paramContext, String paramString)
  {
    try
    {
      InputStream localInputStream2 = paramContext.getResources().getAssets().open(paramString, 3);
      localInputStream1 = localInputStream2;
    }
    catch (IOException localIOException1)
    {
      for (;;)
      {
        BufferedReader localBufferedReader;
        StringBuilder localStringBuilder;
        localIOException1.printStackTrace();
        InputStream localInputStream1 = null;
      }
    }
    localBufferedReader = new BufferedReader(new InputStreamReader(localInputStream1));
    localStringBuilder = new StringBuilder();
    try
    {
      for (;;)
      {
        String str = localBufferedReader.readLine();
        if (str == null) {
          break;
        }
        localStringBuilder.append(str);
      }
      return localStringBuilder.toString();
    }
    catch (IOException localIOException2)
    {
      localIOException2.printStackTrace();
    }
  }
  
  private String getBaseDocument(Document paramDocument)
  {
    Element localElement = (Element)xpath(paramDocument, "/Factory/BaseDocument", XPathConstants.NODE);
    if (localElement != null) {}
    for (String str = localElement.getAttribute("document"); str != null; str = null) {
      return str.substring(0, str.indexOf(".")) + ".dat";
    }
    return str;
  }
  
  public static XMLDataStorage instance()
  {
    if (mInstance == null) {
      mInstance = new XMLDataStorage();
    }
    return mInstance;
  }
  
  private Element[] makeElementArray(NodeList paramNodeList)
  {
    Element[] arrayOfElement = new Element[paramNodeList.getLength()];
    for (int i = 0; i < paramNodeList.getLength(); i++) {
      arrayOfElement[i] = ((Element)paramNodeList.item(i));
    }
    return arrayOfElement;
  }
  
  private boolean parseAsset(Context paramContext, String paramString)
  {
    for (;;)
    {
      try
      {
        byte[] arrayOfByte1 = Base64.decode(convertIStoString(paramContext, paramString), 0);
        FtUtil.log_i("XMLDataStorage", "parseAsset", "Convert dat file: " + paramString);
        localDocument1 = this.mDOMParser.parse(convertBytesToIS(arrayOfByte1));
        String str = getBaseDocument(localDocument1);
        if (str == null) {
          continue;
        }
        FtUtil.log_d("XMLDataStorage", "parseAsset", "Decode base file: " + str);
        byte[] arrayOfByte2 = Base64.decode(convertIStoString(paramContext, str), 0);
        Document localDocument2 = this.mDOMParser.parse(convertBytesToIS(arrayOfByte2));
        redefinitionById(localDocument2, localDocument1);
        swapNode(localDocument2, localDocument2.getElementsByTagName("FactoryTestMenu").item(0), localDocument1.getElementsByTagName("FactoryTestMenu").item(0));
        if ((paramContext.getPackageManager().hasSystemFeature("android.hardware.dual_lcd")) || ("GT-N7108".equals(SystemProperties.get("ro.product.model", "NONE").trim().toUpperCase())))
        {
          FtUtil.log_d("XMLDataStorage", "parseAsset", "FactoryTestMenuSub");
          swapNode(localDocument2, localDocument2.getElementsByTagName("FactoryTestMenuSub").item(0), localDocument1.getElementsByTagName("FactoryTestMenuSub").item(0));
        }
        this.mDocument = localDocument2;
        this.mWasCompletedParsing = true;
      }
      catch (Exception localException)
      {
        Document localDocument1;
        this.mWasCompletedParsing = false;
        FtUtil.log_e(localException);
        continue;
      }
      return this.mWasCompletedParsing;
      this.mDocument = localDocument1;
    }
  }
  
  private void redefinitionById(Document paramDocument1, Document paramDocument2)
  {
    redefinitionById(paramDocument1, paramDocument2.getDocumentElement());
  }
  
  private void redefinitionById(Document paramDocument, Element paramElement)
  {
    if (paramElement.hasAttributes())
    {
      String str1 = paramElement.getAttribute("id");
      if ((str1 != null) && (!str1.isEmpty()))
      {
        FtUtil.log_d("XMLDataStorage", "redefinitionById", "id=" + str1);
        String[] arrayOfString = getAttributeNameSet(paramElement);
        int j = arrayOfString.length;
        int k = 0;
        while (k < j)
        {
          String str2 = arrayOfString[k];
          if (!str2.equals("id"))
          {
            Element localElement = paramDocument.getElementById(str1);
            if (localElement != null) {
              localElement.setAttribute(str2, paramElement.getAttribute(str2));
            }
          }
          else
          {
            k++;
            continue;
          }
          throw new ElementIdMismatchException("Element \"" + str1 + "\" does not exist in base document.");
        }
      }
    }
    if (paramElement.hasChildNodes())
    {
      NodeList localNodeList = paramElement.getChildNodes();
      for (int i = 0; i < localNodeList.getLength(); i++) {
        if (localNodeList.item(i).getNodeType() == 1) {
          redefinitionById(paramDocument, (Element)localNodeList.item(i));
        }
      }
    }
  }
  
  private void swapNode(Document paramDocument, Node paramNode1, Node paramNode2)
  {
    paramNode1.getParentNode().replaceChild(cloneNode(paramDocument, (Element)paramNode2), paramNode1);
  }
  
  private Object xpath(String paramString, QName paramQName)
  {
    return xpath(this.mDocument, paramString, paramQName);
  }
  
  private Object xpath(Document paramDocument, String paramString, QName paramQName)
  {
    try
    {
      Object localObject = this.mXPath.compile(paramString).evaluate(paramDocument, paramQName);
      return localObject;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public String[] getAttributeNameSet(Element paramElement)
  {
    boolean bool = paramElement.hasAttributes();
    String[] arrayOfString = null;
    if (bool)
    {
      NamedNodeMap localNamedNodeMap = paramElement.getAttributes();
      arrayOfString = new String[localNamedNodeMap.getLength()];
      for (int i = 0; i < localNamedNodeMap.getLength(); i++) {
        arrayOfString[i] = localNamedNodeMap.item(i).getNodeName();
      }
    }
    return arrayOfString;
  }
  
  public String getAttributeValueByAttribute(String paramString1, String paramString2, String paramString3)
  {
    if (getElementByAttribute(paramString1, paramString2) != null) {
      return getElementByAttribute(paramString1, paramString2).getAttribute(paramString3);
    }
    return null;
  }
  
  public String getAttributeValueById(String paramString1, String paramString2)
  {
    return this.mDocument.getElementById(paramString1).getAttribute(paramString2);
  }
  
  public String getAttributeValueByTag(String paramString1, String paramString2)
  {
    return ((Element)this.mDocument.getElementsByTagName(paramString1).item(0)).getAttribute(paramString2);
  }
  
  public Element[] getChildElementSet(String paramString)
  {
    return makeElementArray((NodeList)xpath("//" + paramString + "/*", XPathConstants.NODESET));
  }
  
  public Element getElementByAttribute(String paramString1, String paramString2)
  {
    return (Element)xpath("//*[@" + paramString1 + "=" + "'" + paramString2 + "']", XPathConstants.NODE);
  }
  
  /* Error */
  public boolean parseXML(Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic 389	com/sec/factory/support/XMLDataStorage:instance	()Lcom/sec/factory/support/XMLDataStorage;
    //   5: invokevirtual 392	com/sec/factory/support/XMLDataStorage:wasCompletedParsing	()Z
    //   8: ifne +729 -> 737
    //   11: ldc_w 394
    //   14: ldc_w 396
    //   17: ldc_w 398
    //   20: invokestatic 281	android/os/SystemProperties:get	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   23: invokevirtual 291	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   26: istore 4
    //   28: ldc_w 273
    //   31: ldc_w 400
    //   34: invokestatic 281	android/os/SystemProperties:get	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   37: astore 5
    //   39: ldc_w 402
    //   42: ldc_w 400
    //   45: invokestatic 281	android/os/SystemProperties:get	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   48: astore 6
    //   50: ldc_w 404
    //   53: ldc_w 400
    //   56: invokestatic 281	android/os/SystemProperties:get	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   59: astore 7
    //   61: aload 5
    //   63: astore 8
    //   65: ldc 221
    //   67: ldc_w 405
    //   70: new 158	java/lang/StringBuilder
    //   73: dup
    //   74: invokespecial 159	java/lang/StringBuilder:<init>	()V
    //   77: ldc_w 407
    //   80: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   83: iload 4
    //   85: invokevirtual 410	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   88: invokevirtual 172	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   91: invokestatic 243	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   94: iload 4
    //   96: ifeq +518 -> 614
    //   99: new 158	java/lang/StringBuilder
    //   102: dup
    //   103: invokespecial 159	java/lang/StringBuilder:<init>	()V
    //   106: aload 5
    //   108: invokevirtual 284	java/lang/String:trim	()Ljava/lang/String;
    //   111: ldc_w 412
    //   114: ldc_w 414
    //   117: invokevirtual 418	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   120: getstatic 424	java/util/Locale:ENGLISH	Ljava/util/Locale;
    //   123: invokevirtual 428	java/lang/String:toLowerCase	(Ljava/util/Locale;)Ljava/lang/String;
    //   126: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   129: ldc_w 430
    //   132: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   135: invokevirtual 172	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   138: astore 9
    //   140: new 158	java/lang/StringBuilder
    //   143: dup
    //   144: invokespecial 159	java/lang/StringBuilder:<init>	()V
    //   147: aload 6
    //   149: invokevirtual 284	java/lang/String:trim	()Ljava/lang/String;
    //   152: ldc_w 412
    //   155: ldc_w 414
    //   158: invokevirtual 418	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   161: getstatic 424	java/util/Locale:ENGLISH	Ljava/util/Locale;
    //   164: invokevirtual 428	java/lang/String:toLowerCase	(Ljava/util/Locale;)Ljava/lang/String;
    //   167: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   170: ldc_w 430
    //   173: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   176: invokevirtual 172	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   179: astore 10
    //   181: new 158	java/lang/StringBuilder
    //   184: dup
    //   185: invokespecial 159	java/lang/StringBuilder:<init>	()V
    //   188: aload 7
    //   190: invokevirtual 284	java/lang/String:trim	()Ljava/lang/String;
    //   193: ldc_w 412
    //   196: ldc_w 414
    //   199: invokevirtual 418	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   202: getstatic 424	java/util/Locale:ENGLISH	Ljava/util/Locale;
    //   205: invokevirtual 428	java/lang/String:toLowerCase	(Ljava/util/Locale;)Ljava/lang/String;
    //   208: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   211: ldc_w 430
    //   214: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   217: invokevirtual 172	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   220: astore 11
    //   222: ldc 221
    //   224: ldc_w 405
    //   227: new 158	java/lang/StringBuilder
    //   230: dup
    //   231: invokespecial 159	java/lang/StringBuilder:<init>	()V
    //   234: ldc_w 432
    //   237: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   240: aload 9
    //   242: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   245: invokevirtual 172	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   248: invokestatic 243	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   251: ldc 221
    //   253: ldc_w 405
    //   256: new 158	java/lang/StringBuilder
    //   259: dup
    //   260: invokespecial 159	java/lang/StringBuilder:<init>	()V
    //   263: ldc_w 434
    //   266: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   269: aload 10
    //   271: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   274: invokevirtual 172	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   277: invokestatic 243	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   280: ldc 221
    //   282: ldc_w 405
    //   285: new 158	java/lang/StringBuilder
    //   288: dup
    //   289: invokespecial 159	java/lang/StringBuilder:<init>	()V
    //   292: ldc_w 436
    //   295: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   298: aload 11
    //   300: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   303: invokevirtual 172	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   306: invokestatic 243	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   309: aload 9
    //   311: iconst_m1
    //   312: aload 9
    //   314: ldc 190
    //   316: invokevirtual 196	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   319: iadd
    //   320: invokevirtual 440	java/lang/String:charAt	(I)C
    //   323: bipush 48
    //   325: if_icmplt +22 -> 347
    //   328: aload 9
    //   330: iconst_m1
    //   331: aload 9
    //   333: ldc 190
    //   335: invokevirtual 196	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   338: iadd
    //   339: invokevirtual 440	java/lang/String:charAt	(I)C
    //   342: bipush 57
    //   344: if_icmple +78 -> 422
    //   347: new 158	java/lang/StringBuilder
    //   350: dup
    //   351: invokespecial 159	java/lang/StringBuilder:<init>	()V
    //   354: aload 9
    //   356: iconst_0
    //   357: iconst_m1
    //   358: aload 9
    //   360: ldc 190
    //   362: invokevirtual 196	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   365: iadd
    //   366: invokevirtual 200	java/lang/String:substring	(II)Ljava/lang/String;
    //   369: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   372: ldc 202
    //   374: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   377: invokevirtual 172	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   380: astore 8
    //   382: ldc 221
    //   384: ldc_w 405
    //   387: ldc_w 442
    //   390: invokestatic 243	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   393: ldc 221
    //   395: ldc_w 405
    //   398: new 158	java/lang/StringBuilder
    //   401: dup
    //   402: invokespecial 159	java/lang/StringBuilder:<init>	()V
    //   405: ldc_w 444
    //   408: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   411: aload 8
    //   413: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   416: invokevirtual 172	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   419: invokestatic 243	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   422: invokestatic 389	com/sec/factory/support/XMLDataStorage:instance	()Lcom/sec/factory/support/XMLDataStorage;
    //   425: aload_1
    //   426: aload 9
    //   428: invokespecial 446	com/sec/factory/support/XMLDataStorage:parseAsset	(Landroid/content/Context;Ljava/lang/String;)Z
    //   431: ifne +177 -> 608
    //   434: ldc 221
    //   436: ldc_w 405
    //   439: new 158	java/lang/StringBuilder
    //   442: dup
    //   443: invokespecial 159	java/lang/StringBuilder:<init>	()V
    //   446: ldc_w 448
    //   449: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   452: aload 9
    //   454: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   457: invokevirtual 172	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   460: invokestatic 450	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   463: invokestatic 389	com/sec/factory/support/XMLDataStorage:instance	()Lcom/sec/factory/support/XMLDataStorage;
    //   466: aload_1
    //   467: aload 8
    //   469: invokespecial 446	com/sec/factory/support/XMLDataStorage:parseAsset	(Landroid/content/Context;Ljava/lang/String;)Z
    //   472: ifne +136 -> 608
    //   475: ldc 221
    //   477: ldc_w 405
    //   480: new 158	java/lang/StringBuilder
    //   483: dup
    //   484: invokespecial 159	java/lang/StringBuilder:<init>	()V
    //   487: ldc_w 452
    //   490: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   493: aload 8
    //   495: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   498: invokevirtual 172	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   501: invokestatic 450	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   504: invokestatic 389	com/sec/factory/support/XMLDataStorage:instance	()Lcom/sec/factory/support/XMLDataStorage;
    //   507: aload_1
    //   508: aload 10
    //   510: invokespecial 446	com/sec/factory/support/XMLDataStorage:parseAsset	(Landroid/content/Context;Ljava/lang/String;)Z
    //   513: ifne +95 -> 608
    //   516: ldc 221
    //   518: ldc_w 405
    //   521: new 158	java/lang/StringBuilder
    //   524: dup
    //   525: invokespecial 159	java/lang/StringBuilder:<init>	()V
    //   528: ldc_w 454
    //   531: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   534: aload 10
    //   536: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   539: invokevirtual 172	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   542: invokestatic 450	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   545: invokestatic 389	com/sec/factory/support/XMLDataStorage:instance	()Lcom/sec/factory/support/XMLDataStorage;
    //   548: aload_1
    //   549: aload 11
    //   551: invokespecial 446	com/sec/factory/support/XMLDataStorage:parseAsset	(Landroid/content/Context;Ljava/lang/String;)Z
    //   554: ifne +54 -> 608
    //   557: ldc 221
    //   559: ldc_w 405
    //   562: new 158	java/lang/StringBuilder
    //   565: dup
    //   566: invokespecial 159	java/lang/StringBuilder:<init>	()V
    //   569: ldc_w 456
    //   572: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   575: aload 11
    //   577: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   580: invokevirtual 172	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   583: invokestatic 450	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   586: invokestatic 389	com/sec/factory/support/XMLDataStorage:instance	()Lcom/sec/factory/support/XMLDataStorage;
    //   589: aload_1
    //   590: ldc_w 458
    //   593: invokespecial 446	com/sec/factory/support/XMLDataStorage:parseAsset	(Landroid/content/Context;Ljava/lang/String;)Z
    //   596: pop
    //   597: ldc 221
    //   599: ldc_w 405
    //   602: ldc_w 460
    //   605: invokestatic 450	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   608: iconst_1
    //   609: istore_3
    //   610: aload_0
    //   611: monitorexit
    //   612: iload_3
    //   613: ireturn
    //   614: new 158	java/lang/StringBuilder
    //   617: dup
    //   618: invokespecial 159	java/lang/StringBuilder:<init>	()V
    //   621: aload 5
    //   623: invokevirtual 284	java/lang/String:trim	()Ljava/lang/String;
    //   626: ldc_w 412
    //   629: ldc_w 414
    //   632: invokevirtual 418	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   635: getstatic 424	java/util/Locale:ENGLISH	Ljava/util/Locale;
    //   638: invokevirtual 428	java/lang/String:toLowerCase	(Ljava/util/Locale;)Ljava/lang/String;
    //   641: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   644: ldc 202
    //   646: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   649: invokevirtual 172	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   652: astore 9
    //   654: new 158	java/lang/StringBuilder
    //   657: dup
    //   658: invokespecial 159	java/lang/StringBuilder:<init>	()V
    //   661: aload 6
    //   663: invokevirtual 284	java/lang/String:trim	()Ljava/lang/String;
    //   666: ldc_w 412
    //   669: ldc_w 414
    //   672: invokevirtual 418	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   675: getstatic 424	java/util/Locale:ENGLISH	Ljava/util/Locale;
    //   678: invokevirtual 428	java/lang/String:toLowerCase	(Ljava/util/Locale;)Ljava/lang/String;
    //   681: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   684: ldc 202
    //   686: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   689: invokevirtual 172	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   692: astore 10
    //   694: new 158	java/lang/StringBuilder
    //   697: dup
    //   698: invokespecial 159	java/lang/StringBuilder:<init>	()V
    //   701: aload 7
    //   703: invokevirtual 284	java/lang/String:trim	()Ljava/lang/String;
    //   706: ldc_w 412
    //   709: ldc_w 414
    //   712: invokevirtual 418	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   715: getstatic 424	java/util/Locale:ENGLISH	Ljava/util/Locale;
    //   718: invokevirtual 428	java/lang/String:toLowerCase	(Ljava/util/Locale;)Ljava/lang/String;
    //   721: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   724: ldc 202
    //   726: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   729: invokevirtual 172	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   732: astore 11
    //   734: goto -512 -> 222
    //   737: ldc 221
    //   739: ldc_w 462
    //   742: ldc_w 464
    //   745: invokestatic 228	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   748: iconst_0
    //   749: istore_3
    //   750: goto -140 -> 610
    //   753: astore_2
    //   754: aload_0
    //   755: monitorexit
    //   756: aload_2
    //   757: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	758	0	this	XMLDataStorage
    //   0	758	1	paramContext	Context
    //   753	4	2	localObject	Object
    //   609	141	3	bool1	boolean
    //   26	69	4	bool2	boolean
    //   37	585	5	str1	String
    //   48	614	6	str2	String
    //   59	643	7	str3	String
    //   63	431	8	str4	String
    //   138	515	9	str5	String
    //   179	514	10	str6	String
    //   220	513	11	str7	String
    // Exception table:
    //   from	to	target	type
    //   2	61	753	finally
    //   65	94	753	finally
    //   99	222	753	finally
    //   222	347	753	finally
    //   347	422	753	finally
    //   422	608	753	finally
    //   614	734	753	finally
    //   737	748	753	finally
  }
  
  public boolean wasCompletedParsing()
  {
    return this.mWasCompletedParsing;
  }
  
  public class ElementIdMismatchException
    extends RuntimeException
  {
    private static final long serialVersionUID = 5195850516633538384L;
    
    public ElementIdMismatchException(String paramString)
    {
      super();
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.support.XMLDataStorage
 * JD-Core Version:    0.7.1
 */