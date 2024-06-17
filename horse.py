s = "hrse"

p = "rs"

def naive(s, p):
    i = 0
    j = 0
    
    n = len(s)
    m = len(p)
    
    while i < n-m:
        while s[i+j] == p[j]:
            j +=1
            if j==m:
                return i

        i += 1
        j =0

    return -1


print(naive(s ,p))