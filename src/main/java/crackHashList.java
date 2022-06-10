import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class crackHashList {
    private final long[] denyHashCodes = new long[]{
            -8720046426850100497L,
            -8109300701639721088L,
            -7966123100503199569L,
            -7766605818834748097L,
            -6835437086156813536L,
            -4837536971810737970L,
            -4082057040235125754L,
            -2364987994247679115L,
            -1872417015366588117L,
            -254670111376247151L,
            -190281065685395680L,
            33238344207745342L,
            313864100207897507L,
            1203232727967308606L,
            1502845958873959152L,
            3547627781654598988L,
            3730752432285826863L,
            3794316665763266033L,
            4147696707147271408L,
            5347909877633654828L,
            5450448828334921485L,
            5751393439502795295L,
            5944107969236155580L,
            6742705432718011780L,
            7179336928365889465L,
            7442624256860549330L,
            8838294710098435315L
    };
    final long BASIC = 0xcbf29ce484222325L;
    final long PRIME = 0x100000001b3L;
    private ArrayList<String> pkgTestList = new ArrayList<>();

    crackHashList(String packageNameList) throws IOException {
        FileReader fr = new FileReader(packageNameList);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line=br.readLine()) != null) {
            pkgTestList.add(line);
            //System.out.println(line);
        }
        fr.close();
    }
    private boolean hashCrack(String className) {
        if(className.length() < 1){
            return false;
        }
        long hash = (((((BASIC ^ className.charAt(0))
                * PRIME)
                ^ className.charAt(1))
                * PRIME)
                ^ className.charAt(2))
                * PRIME;
        for (int i = 3; i < className.length(); ++i) {
            char c = className.charAt(i);
            hash ^= c;
            hash *= PRIME;
        }
        /* >= 返回在黑名单内的类 ； < 会返回不在黑名单里的类 */
        return Arrays.binarySearch(denyHashCodes, hash) >= 0;
    }

    public void takeListToCrack(){
        pkgTestList.forEach((classname) -> {
            boolean hit = hashCrack(classname);
            if(hit){
                System.out.println(classname);
            }
        });
    }

    public static void main(String[] args) throws IOException {
        if(args[0].equals("-f")){
            crackHashList pkgList=new crackHashList(args[1]);
            pkgList.takeListToCrack();
        }else{
            System.out.println("-f <file path>");
        }
    }
}
