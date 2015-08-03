package main

import "fmt"
import "log"
import "bufio"
import "os"
import "strings"

func main() {
	file, err := os.Open(os.Args[1])
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	var one int32 = 49

	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		var line string = scanner.Text()
		var lineArray []string = strings.Fields(line)
		var word string = lineArray[0]
		var code string = lineArray[1]

		for i, r := range code {
			if r == one {
				fmt.Print(strings.ToUpper(string(word[i])))
			} else {
				fmt.Print(string(word[i]))
			}
		}
		fmt.Println()
	}
}