
public class Hashtable {

    int size;
    HashNode[] bucket;
    double thrashld = (float) 0.5;
    int entryCounter = 0;

    Hashtable(){
        size = 10000;
        bucket = new HashNode[size];
        entryCounter = 0;
    }

    public boolean containsKey(String key) {
        return bucket[getHash(key)] != null;
    }


    public String get(String key){
        HashNode head = bucket[getHash(key)];

        while(head != null){
            if (head.key == key){
                return head.value;
            } else {
                head = head.next;
            }
        }
        return null;
    }

    public void put (String key, String value){
        HashNode head = bucket[getHash(key)];
        if (head == null){
            bucket[getHash(key)] = new HashNode(key, value);
        } else {
            while (head != null) {
                if (head.key.equals(key)) {
                    head.value = value;
                    return;
                }
                head = head.next;
            }
            HashNode node = new HashNode(key, value);
            node.next = bucket[getHash(key)];
            bucket[getHash(key)] = node;
        }
        entryCounter++;
        if ((entryCounter * 1.0) / size >= thrashld){
            increaseBucketSize();
        }
    }


    public String remove(String key) throws Exception {
        HashNode head = bucket[getHash(key)];

        if (head != null) {
            if (head.key.equals(key)) {
                bucket[getHash(key)] = head.next;
                entryCounter--;
                return head.value;
            } else {
                HashNode prev = head;
                HashNode curr;

                while (head.next != null) {
                    curr = head.next;
                    if (curr != null && curr.key == key) {
                        head.next = curr.next;
                        entryCounter--;
                        return curr.value;
                    }
                }
                return head.next.value;
            }
        } else {
            throw new Exception();
        }
    }

    public int getHash(String key){
        return Math.abs(key.hashCode() % size);
    }
    private void increaseBucketSize(){
        HashNode[] temp = bucket;
        size = size *2;
        bucket = new HashNode[size];
        entryCounter = 0;
        for(HashNode head : temp) {
            while(head != null) {
                put(head.key, head.value);
                head = head.next;
            }
        }
    }
}