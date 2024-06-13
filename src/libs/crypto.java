package libs;

public class crypto {

    public String encrypt(String text) {
        
        String result = "";
        String[] values = new String[] {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

        String[][] value = new String[values.length][values.length];
        for(int b=0; b<values.length; b++)
             for(int k=0; k<values.length; k++)
                value[b][k] = values[b+k>values.length-1?b+k-values.length:b+k]; //String.valueOf(b+k>9?b+k-values.length:b+k);

        String key = "130981";
        int intkey = 0;

        for(int l=0; l<text.length(); l++) {
            String tempkey = key.substring(intkey, intkey+1);
            //System.out.println("key ke-" + (intkey+1) + ": " + tempkey);

            String temp = text.substring(l, l+1);
            //System.out.println("text ke-" + (l+1) + ": " + temp);

            for(int kolom=0; kolom<values.length; kolom++) {
                if(temp.equalsIgnoreCase(value[0][kolom])) {
                    for(int baris=0; baris<values.length; baris++) {
                        if(tempkey.equalsIgnoreCase(value[baris][0])) {
                            result += value[baris][kolom];
                            break;
                        }
                    }
                    break;
                }

            }
            intkey++;
            if(intkey==key.length()) intkey=0;

        }
        return result;
    }


    public String decrypt(String text) {

        String result = "";

        String[] values = new String[] {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

        String[][] value = new String[values.length][values.length];
        for(int b=0; b<values.length; b++)
             for(int k=0; k<values.length; k++)
                value[b][k] = values[b+k>values.length-1?b+k-values.length:b+k];//String.valueOf(b+k>9?b+k-values.length:b+k);

        String key = "130981";
        int intkey = 0;

        for(int l=0; l<text.length(); l++) {

            String tempkey = key.substring(intkey, intkey+1);
            //System.out.println("key ke-" + (intkey+1) + ": " + tempkey);

            String temp = text.substring(l, l+1);
            //System.out.println("text ke-" + (l+1) + ": " + temp);

            for(int baris=0; baris<values.length; baris++) {
                if(tempkey.equalsIgnoreCase(value[baris][0])) { // dapet baris
                    for(int kolom=0; kolom<values.length; kolom++) {
                        if(temp.equalsIgnoreCase(value[baris][kolom])) {
                            result += value[0][kolom];
                            break;
                        }
                    }
                    break;
                }
            }
            intkey++;
            if(intkey==key.length()) intkey=0;

        }
        return result;
    }

//    public static void main(String[] args) {
//        crypto cr = new crypto();
//
//        String text = "1309198108011987";
//        String encrypt = cr.encrypt(text);
//        String decrypt = cr.decrypt(encrypt);
//
//        System.out.println("TEXT: " + text);
//        System.out.println("ENCR: " + encrypt);
//        System.out.println("DECR: " + decrypt);
//
//    }
}
