

import java.math.BigDecimal;

/**
* ����Java�ļ����Ͳ��ܹ���ȷ�ĶԸ������������㣬����������ṩ��
* ȷ�ĸ��������㣬�����Ӽ��˳����������롣
*/
public class Arith
{//Ĭ�ϳ������㾫��
   private static final int DEF_DIV_SCALE = 10;

   //����಻��ʵ����
   private Arith()
   {
   }

   /**
    * �ṩ��ȷ�ļӷ����㡣
    * @param v1 ������
    * @param v2 ����
    * @return ���������ĺ�
    */
   public static double add(double v1, double v2)
   {
      BigDecimal b1 = new BigDecimal(Double.toString(v1));
      BigDecimal b2 = new BigDecimal(Double.toString(v2));
      double result = b1.add(b2).doubleValue();
      result = div(result, 1.0, 2);//��λС���Ĵ���
      return result;
   }

   /**
    * �ṩ��ȷ�ļӷ����㡣
    * @param v1 ������
    * @param v2 ����
    * @return ���������ĺ�
    */
   public static double add(String v1, String v2)
   {
      if (CmUtil.isNull(v1)) v1 = "0";
      if (CmUtil.isNull(v2)) v2 = "0";
      BigDecimal b1 = new BigDecimal(v1);
      BigDecimal b2 = new BigDecimal(v2);
      double result = b1.add(b2).doubleValue();
      result = div(result, 1.0, 2);//��λС���Ĵ���
      return result;
   }

   /**
    * �ṩ��ȷ�ļ������㡣
    * @param v1 ������
    * @param v2 ����
    * @return ���������Ĳ�
    */
   public static double sub(double v1, double v2)
   {
      BigDecimal b1 = new BigDecimal(Double.toString(v1));
      BigDecimal b2 = new BigDecimal(Double.toString(v2));
      double result = b1.subtract(b2).doubleValue();
      result = div(result, 1.0, 2);//��λС���Ĵ���
      return result;
   }

   /**
    * �ṩ��ȷ�ļ������㡣
    * @param v1 ������
    * @param v2 ����
    * @return ���������Ĳ�
    */
   public static double sub(String v1, String v2)
   {
      if (CmUtil.isNull(v1)) v1 = "0";
      if (CmUtil.isNull(v2)) v2 = "0";
      BigDecimal b1 = new BigDecimal(v1);
      BigDecimal b2 = new BigDecimal(v2);
      double result = b1.subtract(b2).doubleValue();
      result = div(result, 1.0, 2);//��λС���Ĵ���
      return result;
   }

   /**
    * �ṩ��ȷ�ĳ˷����㡣
    * @param v1 ������
    * @param v2 ����
    * @return ���������Ļ�
    */
   public static double mul(double v1, double v2)
   {
      //      BigDecimal b1 = new BigDecimal(Double.toString(v1));
      //      BigDecimal b2 = new BigDecimal(Double.toString(v2));
      //      return b1.multiply(b2).doubleValue();
      return mul(Double.toString(v1), Double.toString(v2));
   }

   /**
    * �ṩ��ȷ�ĳ˷����㡣
    * @param v1 ������
    * @param v2 ����
    * @return ���������Ļ�
    */
   public static double mul(String v1, String v2)
   {
      if (CmUtil.isNull(v1)) v1 = "0";
      if (CmUtil.isNull(v2)) v2 = "0";
      BigDecimal b1 = new BigDecimal(v1);
      BigDecimal b2 = new BigDecimal(v2);
      double result = b1.multiply(b2).doubleValue();
      result = div(result, 1.0, 2);//��λС���Ĵ���
      return result;
   }

   /**
    * �ṩ����ԣ���ȷ�ĳ������㣬�����������������ʱ����ȷ��
    * С�����Ժ�10λ���Ժ�������������롣
    * @param v1 ������
    * @param v2 ����
    * @return ������������
    */
   public static double div(double v1, double v2)
   {
      return div(v1, v2, DEF_DIV_SCALE);
   }

   /**
    * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ
    * �����ȣ��Ժ�������������롣
    * @param v1 ������
    * @param v2 ����
    * @param scale ��ʾ��ʾ��Ҫ��ȷ��С�����Ժ�λ��
    * @return ������������
    */
   public static double div(double v1, double v2, int scale)
   {
      if (scale < 0)
      {
         throw new IllegalArgumentException(
            "The scale must be a positive integer or zero");
      }
      BigDecimal b1 = new BigDecimal(Double.toString(v1));
      BigDecimal b2 = new BigDecimal(Double.toString(v2));
      return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
   }

   /**
    * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ
    * �����ȣ��Ժ�������������롣
    * @param v1 ������
    * @param v2 ����
    * @param scale ��ʾ��ʾ��Ҫ��ȷ��С�����Ժ�λ��
    * @return ������������
    */
   public static double div(String v1, String v2, int scale)
   {
      if (scale < 0)
      {
         throw new IllegalArgumentException(
            "The scale must be a positive integer or zero");
      }
      if (CmUtil.isNull(v1)) v1 = "0";
      if (CmUtil.isNull(v2)) v2 = "1";
      BigDecimal b1 = new BigDecimal(v1);
      BigDecimal b2 = new BigDecimal(v2);
      return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
   }

   /**
    * �ṩ��ȷ��С��λ�������봦��
    * @param v ��Ҫ�������������
    * @param scale С���������λ
    * @return ���������Ľ��
    */
   public static double round(double v, int scale)
   {

      if (scale < 0)
      {
         throw new IllegalArgumentException(
            "The scale must be a positive integer or zero");
      }
      BigDecimal b = new BigDecimal(Double.toString(v));
      BigDecimal one = new BigDecimal("1");
      return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
   }

   public static void main(String[] arg)
   {
      String a = "01";
      String b = Arith.div(a, "100", 2) + "";
      System.out.println(b);

      String x = "19.80", y = "0.07";
      String z = Arith.mul(x, y) + "";
      System.out.println(z);
      
      String e = "1.010";
      String r = Arith.mul(e, "100") + "";
      r = r.substring(0, r.indexOf("."));
      System.out.println(r);
   }
}
