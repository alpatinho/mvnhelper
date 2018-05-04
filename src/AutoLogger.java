import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

class AutoLogger {

    private final Robot robo;
    private Util util = new Util();

    AutoLogger() throws AWTException {
        this.robo = new Robot();
    }

    void login (String login, String senha, String agencia){
        alteraSelecao();
        escreve(login, agencia);
        digito(KeyEvent.VK_ENTER);
        escreve(senha, agencia);
        digito(KeyEvent.VK_ENTER);
    }

    private void alteraSelecao(){
        robo.keyPress(KeyEvent.VK_ALT);
        robo.keyPress(KeyEvent.VK_TAB);
        robo.keyRelease(KeyEvent.VK_TAB);
        robo.keyRelease(KeyEvent.VK_ALT);
    }

    private void escreve(String palavra, String agencia) {
        for (int i = 0; i < palavra.length(); i++) {
            if(palavra.charAt(i) == '#'){
                escreve(agencia, null);
            }
            escritor(palavra.charAt(i));
        }
    }

    private void escritor(char caracter) {
        switch (caracter) {
            case 'a': digito(KeyEvent.VK_A);
                break;
            case 'b': digito(KeyEvent.VK_B);
                break;
            case 'c': digito(KeyEvent.VK_C);
                break;
            case 'd': digito(KeyEvent.VK_D);
                break;
            case 'e': digito(KeyEvent.VK_E);
                break;
            case 'f': digito(KeyEvent.VK_F);
                break;
            case 'g': digito(KeyEvent.VK_G);
                break;
            case 'h': digito(KeyEvent.VK_H);
                break;
            case 'i': digito(KeyEvent.VK_I);
                break;
            case 'j': digito(KeyEvent.VK_J);
                break;
            case 'k': digito(KeyEvent.VK_K);
                break;
            case 'l': digito(KeyEvent.VK_L);
                break;
            case 'm': digito(KeyEvent.VK_M);
                break;
            case 'n': digito(KeyEvent.VK_N);
                break;
            case 'o': digito(KeyEvent.VK_O);
                break;
            case 'p': digito(KeyEvent.VK_P);
                break;
            case 'q': digito(KeyEvent.VK_Q);
                break;
            case 'r': digito(KeyEvent.VK_R);
                break;
            case 's': digito(KeyEvent.VK_S);
                break;
            case 't': digito(KeyEvent.VK_T);
                break;
            case 'u': digito(KeyEvent.VK_U);
                break;
            case 'v': digito(KeyEvent.VK_V);
                break;
            case 'w': digito(KeyEvent.VK_W);
                break;
            case 'x': digito(KeyEvent.VK_X);
                break;
            case 'y': digito(KeyEvent.VK_Y);
                break;
            case 'z': digito(KeyEvent.VK_Z);
                break;
            case 'A': shiftDigito(KeyEvent.VK_A);
                break;
            case 'B': shiftDigito(KeyEvent.VK_B);
                break;
            case 'C': shiftDigito(KeyEvent.VK_C);
                break;
            case 'D': shiftDigito(KeyEvent.VK_D);
                break;
            case 'E': shiftDigito(KeyEvent.VK_E);
                break;
            case 'F': shiftDigito(KeyEvent.VK_F);
                break;
            case 'G': shiftDigito(KeyEvent.VK_G);
                break;
            case 'H': shiftDigito(KeyEvent.VK_H);
                break;
            case 'I': shiftDigito(KeyEvent.VK_I);
                break;
            case 'J': shiftDigito(KeyEvent.VK_J);
                break;
            case 'K': shiftDigito(KeyEvent.VK_K);
                break;
            case 'L': shiftDigito(KeyEvent.VK_L);
                break;
            case 'M': shiftDigito(KeyEvent.VK_M);
                break;
            case 'N': shiftDigito(KeyEvent.VK_N);
                break;
            case 'O': shiftDigito(KeyEvent.VK_O);
                break;
            case 'P': shiftDigito(KeyEvent.VK_P);
                break;
            case 'Q': shiftDigito(KeyEvent.VK_Q);
                break;
            case 'R': shiftDigito(KeyEvent.VK_R);
                break;
            case 'S': shiftDigito(KeyEvent.VK_S);
                break;
            case 'T': shiftDigito(KeyEvent.VK_T);
                break;
            case 'U': shiftDigito(KeyEvent.VK_U);
                break;
            case 'V': shiftDigito(KeyEvent.VK_V);
                break;
            case 'W': shiftDigito(KeyEvent.VK_W);
                break;
            case 'X': shiftDigito(KeyEvent.VK_X);
                break;
            case 'Y': shiftDigito(KeyEvent.VK_Y);
                break;
            case 'Z': shiftDigito(KeyEvent.VK_Z);
                break;
            case '0': digito(KeyEvent.VK_0);
                break;
            case '1': digito(KeyEvent.VK_1);
                break;
            case '2': digito(KeyEvent.VK_2);
                break;
            case '3': digito(KeyEvent.VK_3);
                break;
            case '4': digito(KeyEvent.VK_4);
                break;
            case '5': digito(KeyEvent.VK_5);
                break;
            case '6': digito(KeyEvent.VK_6);
                break;
            case '7': digito(KeyEvent.VK_7);
                break;
            case '8': digito(KeyEvent.VK_8);
                break;
            case '9': digito(KeyEvent.VK_9);
                break;
            case '#': // LETRA RESERVADA
                break;
            case '`': digito(KeyEvent.VK_BACK_QUOTE);
                break;
            case '-': digito(KeyEvent.VK_MINUS);
                break;
            case '=': digito(KeyEvent.VK_EQUALS);
                break;
            case '!': shiftDigito(KeyEvent.VK_1);
                break;
            case '@': shiftDigito(KeyEvent.VK_2);
                break;
            case '$': shiftDigito(KeyEvent.VK_4);
                break;
            case '%': shiftDigito(KeyEvent.VK_5);
                break;
            case '&': shiftDigito(KeyEvent.VK_7);
                break;
            case '*': shiftDigito(KeyEvent.VK_8);
                break;
            case '(': shiftDigito(KeyEvent.VK_9);
                break;
            case ')': shiftDigito(KeyEvent.VK_0);
                break;
            case '_': shiftDigito(KeyEvent.VK_MINUS);
                break;
            case '+': digito(KeyEvent.VK_ADD);
                break;
            case '[': digito(KeyEvent.VK_OPEN_BRACKET);
                break;
            case ']': digito(KeyEvent.VK_CLOSE_BRACKET);
                break;
            case '{': shiftDigito(KeyEvent.VK_OPEN_BRACKET);
                break;
            case '}': shiftDigito(KeyEvent.VK_CLOSE_BRACKET);
                break;
            case '|': shiftDigito(KeyEvent.VK_BACK_SLASH);
                break;
            case ';': digito(KeyEvent.VK_SEMICOLON);
                break;
            case ':': shiftDigito(KeyEvent.VK_SEMICOLON);
                break;
            case '"': shiftDigito(KeyEvent.VK_QUOTE);
                break;
            case ',': digito(KeyEvent.VK_COMMA);
                break;
            case '<': shiftDigito(KeyEvent.VK_COMMA);
                break;
            case '.': digito(KeyEvent.VK_PERIOD);
                break;
            case '>': shiftDigito(KeyEvent.VK_PERIOD);
                break;
            case '?': shiftDigito(KeyEvent.VK_SLASH);
                break;
            case ' ': digito(KeyEvent.VK_SPACE);
                break;
            default: util.exibeMensagem(Util.Mensagens.ERRO_LOGIN_CARACTER_INVALIDO, false);
        }

    }

    private void digito(int letra) {
        robo.delay(50);
        robo.keyPress(letra);
        robo.keyRelease(letra);
    }

    private void shiftDigito(int letra) {
        robo.keyPress(KeyEvent.VK_SHIFT);
        robo.keyPress(letra);
        robo.keyRelease(letra);
        robo.keyRelease(KeyEvent.VK_SHIFT);
    }
}

