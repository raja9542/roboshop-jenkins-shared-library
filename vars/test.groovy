def new1() {
    print "hello"
    def xyz= "hello"
    def x= 10
    print "xyz= ${xyz}"
    print "abc= ${abc}"

    if(abc == "some data") {
        print "yes"
    }
        else{
            print "No"
        }
    def a= 2
    def b= 0
    while(a>b) {
        println "${b}"
        b++

    }

    for(int i= 0; i<5; i++) {
        println(i);
    }
    def fruits= ["Apple", "banana", "orange"]
    for(i in fruits) {
        println(i);
    }
}
