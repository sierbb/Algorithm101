package OOD;

public class RLEIterator {

    private int encodingIdx;
    private int[] encoding;

    public RLEIterator(int[] encoding) {
        encodingIdx = 0; //index in encoding where the pointer is
        this.encoding = encoding;
    }

    public int next(int n) {
        if (encodingIdx >= encoding.length){ //if encoding index == length? maybe invalid encoding input
            return -1;
        }
        if (n <= encoding[encodingIdx]){
            this.encoding[encodingIdx] -= n;
            return this.encoding[encodingIdx+1];
        }else {
            while (encodingIdx < this.encoding.length && n > this.encoding[encodingIdx]){
                //n > current encoding element's count, need to incr encoding list index to look for next elements
                n-= this.encoding[encodingIdx];
                encodingIdx+=2;
            }
            if (encodingIdx < this.encoding.length){ //if not out of bound
                //now the new encodingIdx can handle n
                this.encoding[encodingIdx] -= n;
                return this.encoding[encodingIdx+1];
            }
        }
        return -1;
    }
}

/**
 * Your RLEIterator object will be instantiated and called as such:
 * RLEIterator obj = new RLEIterator(encoding);
 * int param_1 = obj.next(n);
 */

//Step 1: decode into list of element
//[1,  8, 0, 9, 2, 5] -> [8,8,8,5,5] O(n)
//eleCount=0
//idx=0
//Step 2: do the iterator, by running next() for m times depends on the m passed
//
//next(2)  2<= encoding[idx]; encoding[idx]-=n = 1; return encoding[idx+1]
//next(1)  1<= encoding[idx]; encoding[idx]-=n = 0; return encoding[idx+1]; idx+=2;
//next(1)  while 1 > encoding[idx]; idx+=2; 1<= encoding[idx]; encoding[idx]-=n = 1; return encoding[idx+1] = 5;
//next(2) = 2 >= encoding[idx]; idx+=2; encoding is out of bound, return -1

//TC:O(1000) 1000 calls to next(), worst case each call cause encoding idx to move one step, until out of bound
//SC:O(1) no extra DS or recursion