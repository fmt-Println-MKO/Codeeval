package main

import "fmt"
import "log"
import "bufio"
import "os"

func main() {
	file, err := os.Open(os.Args[1])
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	var a1 []byte = []byte{60, 45, 45, 60, 60}
	var a2 []byte = []byte{62, 62, 45, 45, 62}

	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		var line []byte = scanner.Bytes()
		var size int = len(line)

		var j int = 0
		var ca int = 0
		for i := 0; i < size-4; i++ {

			j = 0
			for ; j < 5; j++ {
				if a1[j] != line[i+j] {
					break
				}
			}
			if j == 5 {
				ca++
				i += 3
			} else {
				j = 0
				for ; j < 5; j++ {
					if a2[j] != line[i+j] {
						break
					}
				}
				if j == 5 {
					ca++
					i += 3
				}
			}
		}
		fmt.Println(ca)
	}
}