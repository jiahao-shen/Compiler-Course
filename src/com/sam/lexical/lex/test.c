#include <stdio.h>
int main() {
    int a;
    for (a = 0; a < 5; ++a) {
        if (a % 2 == 0)
            continue;
        printf("Hello %d", a);
    }
    if (true) {
        char b = 'a';
    }
    return 0;
}