class PiCipher {

    constructor() {
        this.ciphertext = "";
        this.pi_arr = [3, 1, 4, 1, 5]
    }

    encrypt(plaintext) {
        plaintext = plaintext.replace(/[^a-z]/gi, '');
        let ciphertext = "";
        for (let x = 0; x < plaintext.length; x++) {
            let factor = this.pi_arr[x % 5];
            let c = plaintext.charAt(x);
            c = c.toUpperCase().charCodeAt(0) - 64;

            if (factor === 1) {
                c += factor;
                while (c > 26) c -= 26;
                c = String.fromCharCode(c + 64);
            }

            else {
                c *= factor;
                while (c > 26) c -= 26;
                c = String.fromCharCode(c + 64);
            }

            ciphertext += c;
        }

        document.getElementById("ciphertext").value = ciphertext;
    }

    decrypt(ciphertext) {
        ciphertext = ciphertext.replace(/[^a-z]/gi, '');
        let plaintext = "";
        for (let x = 0; x < ciphertext.length; x++) {
            let factor = this.pi_arr[x % 5];
            let c = ciphertext.charAt(x);
            c = c.toUpperCase().charCodeAt(0) - 64;

            if (factor === 1) {
                c -= factor;
                if (c < 0) c += 26;
                c = String.fromCharCode(c + 64);
            }

            else {
                let result = "";
                while (c <= 130) {
                    if ((c % factor) === 0 && c / factor <= 26) result += String.fromCharCode(c / factor + 64);
                    c += 26;
                }
                if (result.length > 1) result = "[" + result + "]";
                c = result;
            }

            plaintext += c;
        }

        document.getElementById("plaintext").value = plaintext;
    }
}