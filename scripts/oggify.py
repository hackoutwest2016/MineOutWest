import os
import re
import requests
import json
import subprocess
import sys

PLAYLIST = """
https://open.spotify.com/track/71D4LseR0RADPbyvMeTIHR
https://open.spotify.com/track/3M0YwlWNPX8cfWQjYsXTpu
https://open.spotify.com/track/0XPi8NgpZZ7YkivJjq0Nzt
https://open.spotify.com/track/59aBJF7Nh9dkYbXY7xHZFk
https://open.spotify.com/track/2qUZliZ9NHUYIAZqRNe6pZ
https://open.spotify.com/track/33pDmPx2pkJ7JCAQpRDQ9B
https://open.spotify.com/track/0EM0yABJzbFOvZQkfvuvCy
https://open.spotify.com/track/4w7yrP4RAeeyhfG9nJqQvS
https://open.spotify.com/track/4E92bmgFiHxvy2SNjXjC3b
https://open.spotify.com/track/0osPUefhvYxoB2eZw6prBt
https://open.spotify.com/track/0kA5scXMiaO5Ms2GQ4pRWa
https://open.spotify.com/track/0egYMfS7hjEneR86a9K61w
https://open.spotify.com/track/5zflsn7VI8qyvzrowMoTgF
https://open.spotify.com/track/11X4Gw3T4hoktVruLgTnm1
https://open.spotify.com/track/2bdh8ALxBeuvpnuoh37BDn
https://open.spotify.com/track/2fpthvVGcOCuCBRFUqg0hG
https://open.spotify.com/track/0VEoxxvR26zfJvjUC8k1tt
https://open.spotify.com/track/3Ljs3vL9GJLEzXJBV8yVxe
https://open.spotify.com/track/3nkuemN1DlyHYZCk5eZYLn
https://open.spotify.com/track/3SdOP61TT2l8YQpSaUtsWR
https://open.spotify.com/track/79P7oiTCUliF8AQTzUiK24
https://open.spotify.com/track/2o5nczXJ7wb5oRaBVLS3EU
https://open.spotify.com/track/26xtHEq40YTfjAij523Ml6
https://open.spotify.com/track/2RVCkAsopqqwKHCKKtHVYP
https://open.spotify.com/track/5bew0ChUvFOO6oPp2vsKNP
https://open.spotify.com/track/4wPlMbv1ikPypgeDqBCJvC
https://open.spotify.com/track/17LkgPVD9skzhD3tfbXvWK
https://open.spotify.com/track/28UYIs4bHvBYqc9YiHqFZd
https://open.spotify.com/track/3nrgxv9KvTkwpM68LgSDE8
https://open.spotify.com/track/6oSnzvS5OxzwxSpkJrak3Z
https://open.spotify.com/track/0xDosercBjmUanH8rKV1b2
https://open.spotify.com/track/2JvXeVKEeaSsFZ5Tjn0qwW
https://open.spotify.com/track/4g9WecAmg7OQcz6xlWqRzL
https://open.spotify.com/track/7oQMoMSwDfm9GryPxoWCSi
https://open.spotify.com/track/7BZQhe7vy7E9ZEbAsivsvn
https://open.spotify.com/track/4gI6T79Ga5qhnKzZAugcNw
https://open.spotify.com/track/0zeZ32XGfo6bCuHNLQfNZC
https://open.spotify.com/track/1lp0bwGX8D31vUybJSiCm8
https://open.spotify.com/track/1DLAUPQDyMlqgICfshI1Ja
https://open.spotify.com/track/4saEkFVXDvGXz47ShFACoW
https://open.spotify.com/track/21HfUACOv1lgBiYocruoW8
https://open.spotify.com/track/3qBn2YAFbX16coGmScr7Y5
https://open.spotify.com/track/6r6n5OmXkdsOxN2iCj5UE0
https://open.spotify.com/track/1FNvsOtp6bnSOQTBgJOtha
https://open.spotify.com/track/1eoBkPMFnFIaQ85wJlsBbk
https://open.spotify.com/track/4BAbgJAyNehGwzKRC0FQSt
https://open.spotify.com/track/16NOHRm0OEjmg55OZAxVmV
https://open.spotify.com/track/3j6r6hzRqFPiwCskWr7KRY
https://open.spotify.com/track/4dF34N2GqCY7CufabXEfOZ
https://open.spotify.com/track/16E5plvDJIUAIpHiHiAW7E
https://open.spotify.com/track/3NWTRZ0A8xKlBP1qgNftql
https://open.spotify.com/track/10SUWedx0zohs8M3OJpnA7
https://open.spotify.com/track/2gvkz0Cxw7OtEh2d5G9uEC
https://open.spotify.com/track/4ZJNTpuf8TvHD5ZMijFuW5
https://open.spotify.com/track/4HvtoOpUqL7jehCqze4oGu
https://open.spotify.com/track/2Yc7OZnvTAJcJ8XaNDtFx5
https://open.spotify.com/track/39P2rAbnPqIQKkdzVV1Nfw
https://open.spotify.com/track/3a64SjwnGFzBv7QnOI0D60
https://open.spotify.com/track/04eIYrt0KMQP4MmKEBEYah
https://open.spotify.com/track/0VcqYTCP4gOL9TaXvluIaa
https://open.spotify.com/track/0vyRl9GLwpuI5GIyB9CUX9
https://open.spotify.com/track/08bjJuoHJwfTnjNcuENOFz
https://open.spotify.com/track/2nfLD9IpANrmsO71gBc1bW
https://open.spotify.com/track/73rNZyqMvP2cqhlVskLbcc
https://open.spotify.com/track/2tk6z8eVucsLaED9hrojIX
https://open.spotify.com/track/2VcAgFuGEly1AYUEvHzy0w
https://open.spotify.com/track/5dulWCZyRqio1YhzwCc4P4
https://open.spotify.com/track/77qwHcLbFoTwH11sSWpYQw
https://open.spotify.com/track/1eYX9oRvZFiWrtac2v1H9F
https://open.spotify.com/track/1VcRVDCU4Mv0B9XxtB1o0B
https://open.spotify.com/track/4MR2KuVcWdXwqlAIMJJVNR
https://open.spotify.com/track/1KZyVnyptQcPzkx7ELCnZC
https://open.spotify.com/track/3nnG7AM9QopHVPEuLX3Khk
https://open.spotify.com/track/1WOxmQAMnyvTdTuzZhuTBo
https://open.spotify.com/track/2zojH99hHY6Xl0cxeFkj7o
https://open.spotify.com/track/6zYpKWiF0Q1SNQGKHLrUfD
https://open.spotify.com/track/5FDm33MpQRTmQ8GNcJEwcU
https://open.spotify.com/track/5hewzVVBCTezV0Rq9owofM
https://open.spotify.com/track/7FYchDcrtmYNGHJlfYdsOe
https://open.spotify.com/track/0ImjeKKMwhUNJwVe4JXEDy
https://open.spotify.com/track/2YxM5vNyhtf7J9PJO1chJ2
https://open.spotify.com/track/4ndyBbvPdvr0DSiSWOu3XD
https://open.spotify.com/track/2zThbyqWosKQ9n0qiUfosz
https://open.spotify.com/track/29U1azNyQN2vSqIquF9srD
https://open.spotify.com/track/1CLmFKW99S8eJrebO3GB04
https://open.spotify.com/track/1oy0UIGzuqIPBkOsjojeJ1
https://open.spotify.com/track/5a4d1YVJ3OBFRqgAhRK36o
https://open.spotify.com/track/2ZPkY0z7Opko33fHO9VHHV
https://open.spotify.com/track/5uvIVpDCvC9kTtSjJDbY8C
https://open.spotify.com/track/2Ia3X1Njykhg2OhfHxarmy
https://open.spotify.com/track/7uNXhqgUeqo49GCZv13plx
https://open.spotify.com/track/1z5jOmNswvnzjC5gFLu5Dv
https://open.spotify.com/track/4ddgVJoVrDCJhQIVroFw5B
https://open.spotify.com/track/4nF6z92xovN50xzpgN6TuV
https://open.spotify.com/track/4KXMjHKhad2GwpFEvGl9BE
https://open.spotify.com/track/1ixphys4A3NEXp6MDScfih
https://open.spotify.com/track/5VgVIhrH8TblQ5QRw7ILkn
https://open.spotify.com/track/1v5GSWojANOM4MJj1GYE1s
https://open.spotify.com/track/2AxjntkZpEf5Qt7KoiccHI
https://open.spotify.com/track/6EVTgXDsDsa3rqUQiA6XQE
https://open.spotify.com/track/1JqInwYzD5BNmTUkn8KxS0
https://open.spotify.com/track/40YcuQysJ0KlGQTeGUosTC
https://open.spotify.com/track/0X7y06KlsqnRXI0da0DWc9
https://open.spotify.com/track/6jmTHeoWvBaSrwWttr8Xvu
https://open.spotify.com/track/1wHZx0LgzFHyeIZkUydNXq
https://open.spotify.com/track/51EC3I1nQXpec4gDk0mQyP
https://open.spotify.com/track/4GXl8l1MfZPf2GvpyRPJBf
https://open.spotify.com/track/1cnKr9nO2TjXWyAfcb72We
https://open.spotify.com/track/6EidGY4S6hkU6fwNdSSabg
https://open.spotify.com/track/4u8031pTkTODbRaDCbx8Yv
https://open.spotify.com/track/426CF4TFxQRJ53LZQpuPEZ
https://open.spotify.com/track/4hOXnVyDBZi1vVmkXdzh1a
https://open.spotify.com/track/4xw8UnKYxsiRFjigtQcVAL
https://open.spotify.com/track/4NfKSsUxmT4rZxOLPSIGdY
https://open.spotify.com/track/0Tovb9LxiQIyKxInGFw3IT
https://open.spotify.com/track/7gZzWI6yC0Wb8xLcNYoYIg
https://open.spotify.com/track/6Y9TU1WiogwHVaaWSVjRa3
https://open.spotify.com/track/0zhNoNKhia3RRFzrmHlml6
https://open.spotify.com/track/7w5uV8ylvfdyGWpe7hssfW
https://open.spotify.com/track/0wqurhSsCxrhRBWC434Sjg
https://open.spotify.com/track/27SdWb2rFzO6GWiYDBTD9j
https://open.spotify.com/track/4E3afPSY5fUEelQS9ppL0e
https://open.spotify.com/track/4VrWlk8IQxevMvERoX08iC
https://open.spotify.com/track/6393Du1axGG1NvAMq0OcfU
https://open.spotify.com/track/0Sl8C9oeS3b5Kv9bSvTPDr
https://open.spotify.com/track/5wN0Qf0iHLcWbCugUtQLNp
https://open.spotify.com/track/3JX1VtcqrevOvYy1sE8R0a
https://open.spotify.com/track/0DBpXjoL184xYpn8SlPFWi
https://open.spotify.com/track/4uzUaLgdJWB7vF3vJKbETu
https://open.spotify.com/track/5SfgEjazmsdFInTrL2KBvs
https://open.spotify.com/track/4629a9ZJgYXw39hwWQ8c9O
https://open.spotify.com/track/2tCyPd3UUGD4GaCgkAsFWe
https://open.spotify.com/track/37GKEiiplYAIVdeN5fTc02
https://open.spotify.com/track/67Hna13dNDkZvBpTXRIaOJ
https://open.spotify.com/track/0DRe2MeIAT5Bf1kOhRPJ4H
https://open.spotify.com/track/2BndJYJQ17UcEeUFJP5JmY
https://open.spotify.com/track/2RmherZdlF6lXsA47ELmY9
https://open.spotify.com/track/2R8binPTSDj38hAAIIODLX
https://open.spotify.com/track/7jqief6OzcBx4hE7sRrKye
https://open.spotify.com/track/6vRp1EGkh1SE7uGNhbqVjT
https://open.spotify.com/track/5poLHHu5UKTtnbE3TjLxak
https://open.spotify.com/track/1r0Auy0YiEMhrGEejjh9HH
https://open.spotify.com/track/6tqzOYuE3lrNVPDgtF3MjM
https://open.spotify.com/track/3MH3oRNyFb5mNm663xcK7T
https://open.spotify.com/track/4KDXh5ocFmL4sKLs3UN7fB
https://open.spotify.com/track/6btUHzFhMcTSRJsB055QAz
https://open.spotify.com/track/0Eg1fYRfNkdJoKlE6SKrjE
https://open.spotify.com/track/2IyOUglK8pDlG6j16y3i6B
https://open.spotify.com/track/2oeit7YXlSQLwBxSW19Gu0
https://open.spotify.com/track/0ho6KbJGma0aL3x59zVAfY
https://open.spotify.com/track/60G2RG8fXZBmn4WzSmx5ub
https://open.spotify.com/track/2MNzPyTIfPsxdLX90bXrYt
https://open.spotify.com/track/6xJJcYkwMJ2BGMdmV9o1xb
https://open.spotify.com/track/2LhvXsnUMMiUJVqCkZsRI3
https://open.spotify.com/track/1oiD5zGVKxlMNCxSUXGL9g
https://open.spotify.com/track/4v36nWrqRSwPlCQgQsb7Wx
https://open.spotify.com/track/70JSnplpMA0L55S7A5K8pG
https://open.spotify.com/track/6UZT7Pa3Wp6HFe7LS6zN9a
https://open.spotify.com/track/17Zi730TMDQlpCtRIhk5U5
https://open.spotify.com/track/31AyUvMRDvZjyQI1N05lsT
https://open.spotify.com/track/4DLEvfk4CixDarTMn1Cn2B
https://open.spotify.com/track/4P3L9MQSsM5OMtQjEaYt0I
https://open.spotify.com/track/5qcSy70d147M9uFynKgDgl
https://open.spotify.com/track/2AzIOdWnMAvVrKplInZHoB
https://open.spotify.com/track/76kEf0oIweRqhHvagGmRY4
https://open.spotify.com/track/5JlinWJDKngcX7lBuMIr3V
https://open.spotify.com/track/1MPEPvmX18S9MK4RlMKIst
https://open.spotify.com/track/5XXiK8GZjjmCHpBpUEm9z5
https://open.spotify.com/track/35PI5Hsq17h723EbyyDFsi
https://open.spotify.com/track/4kFfFe38CRVnTsakUTL4E4
https://open.spotify.com/track/4DA95pyBe6QORPGvTEuMWQ
https://open.spotify.com/track/3NoOwvxhI2yMYknxqnFUVx
https://open.spotify.com/track/71YuK59hLjyyzn28YKxuOC
https://open.spotify.com/track/2sGbWFw923NlxNd7NAU5JH
https://open.spotify.com/track/1Tnn0Vsz4jwkm4X5eq5sLN
https://open.spotify.com/track/44HBswFhaYMxqeeGr5kp9V
https://open.spotify.com/track/7EROmP0HzqU9uY8VSKAkQz
https://open.spotify.com/track/3soRnznKdQHTMmuW6wiUDY
https://open.spotify.com/track/0md4Z6wyXaDpK0ukd65Uhv
https://open.spotify.com/track/6UqoDD1qpptzEAkaRl9p4n
https://open.spotify.com/track/2AgPoYYXrzGu8YwBFbCsUZ
https://open.spotify.com/track/34204TEDsGFT2H1lYp3YL5
https://open.spotify.com/track/1MB0g9A3TfYzWYEITAxvUv
https://open.spotify.com/track/0mUSz0dI8d6or6D3oB2njl
https://open.spotify.com/track/3auBEcdyysm3Nc74V06XPx
https://open.spotify.com/track/7IMnGsxf6VkLMWs0d7vVLf
https://open.spotify.com/track/1EUKGwx6LBGls2LZzCFSiK
https://open.spotify.com/track/37UIgyNrb7ibToFaYl4PFS
https://open.spotify.com/track/5d1XBMB6RYsQVwJkj4yu0Q
https://open.spotify.com/track/74Ha7F6flfFBTgUr7Q5gfd
https://open.spotify.com/track/4VJNGwxW8r0lQntVi1F5xi
https://open.spotify.com/track/1yNDrZcLXZVTfqGhkmig52
https://open.spotify.com/track/2nRyuAVsVWXogXYNmjDs3N
https://open.spotify.com/track/0zsEHMPPF33iWehGGtS4su
https://open.spotify.com/track/2JyZlVFqiCmtMjiSbB0ubB
https://open.spotify.com/track/3C6NlKPgpHuY5uNX4rP4j9
https://open.spotify.com/track/7pYhnqnqlFic8XaEL8HSBy
https://open.spotify.com/track/44cviLFPFvRpoxMFdZEW2Y
https://open.spotify.com/track/35BKT5VIQH9D55z5iNtRhK
https://open.spotify.com/track/2aHFv5b0tQ7SY8rreDTBmF
https://open.spotify.com/track/3rz9USQJi0oPy73382G7as
https://open.spotify.com/track/6aQW6UBUuXIE2vggvK1XYU
https://open.spotify.com/track/6bRgyHxbPkvrHiQFSWxo1B
https://open.spotify.com/track/3EvVwGhoLn4VmGImDv7auQ
https://open.spotify.com/track/2NSPLcfjDslsmNhvl1bukj
https://open.spotify.com/track/307UmwtvdyqU0ZLGZ4efTm
https://open.spotify.com/track/7yCdFM1GLDi31wFrgE1d1t
https://open.spotify.com/track/4TptcrHn2B5SBruKEXtdFI
https://open.spotify.com/track/1esVyqukI7BLQq1a95s4gA
https://open.spotify.com/track/1wQ82M8NEbEzUsg4hAPets
https://open.spotify.com/track/3GwJFcGYdpjW0izOl3968P
https://open.spotify.com/track/1S07xdfpKpCClKkg5uPmsy
https://open.spotify.com/track/13hQj3kA6hLlKR9fBK9wCq
https://open.spotify.com/track/4OaNC1j5tRMAZku6P6X4jl
https://open.spotify.com/track/0njlSzbIzuPvDVj3AqIlRT
https://open.spotify.com/track/5cbHsv9rP4bjUFslWAt5pF
https://open.spotify.com/track/0Cjkwdd0w3cazvtZJ5e8ax
https://open.spotify.com/track/1XHFob24QklIXtLRopKirJ
https://open.spotify.com/track/43Y5UfhksjiZF3a4S6XDPI
https://open.spotify.com/track/0MAior6fZ9hfPgzjphQLXk
https://open.spotify.com/track/2aNHMMpclCTUBeFRzHmHBy
https://open.spotify.com/track/6Vk3MMnpioe9MYvoQUgjew
https://open.spotify.com/track/7ldMja9Us2gUrXmIxRWfpl
https://open.spotify.com/track/1Hu3xDaE6SoLKUEXHHRVLk
https://open.spotify.com/track/7LjIjnztu0pzLsSQO0wOha
https://open.spotify.com/track/5lLGyaI3Y6wBU0Ao3DvKSK
https://open.spotify.com/track/74aJOqpvDey7jMRV1Fr0jk
https://open.spotify.com/track/7DaGCWgYaaJW1j4ubuDucv
https://open.spotify.com/track/5W4BxNVIawIvhJQcU75G4O
https://open.spotify.com/track/5wXQG1arZEsR5KQYRWoeJC
https://open.spotify.com/track/1BxgYYPiULKEamYOfB4mtz
https://open.spotify.com/track/4K7EEy5sZVdcdiy4VxrnSk
https://open.spotify.com/track/5JtMuBzQU2XPF6fXoYYSou
https://open.spotify.com/track/7lkAkI6WA6yut21KdRCXt7
https://open.spotify.com/track/2yC534d7m8MkvDwWRPaeSp
https://open.spotify.com/track/6VqDHAFl6EwVWfEA8AV2O8
https://open.spotify.com/track/7vlTYFQzjrcK469M84zJHT
https://open.spotify.com/track/7gROHi7GhhW7BM57VrKFhV
https://open.spotify.com/track/3y7wAKCg8LWlIKXbjzpEyN
https://open.spotify.com/track/6NTBZb6q4jnPyU5Hs0DhhY
https://open.spotify.com/track/366K65mHux5px3rL4Da8qQ
https://open.spotify.com/track/098q0cUqyOgs1gtgBPXLWG
https://open.spotify.com/track/0c2PfhPk3IEOLRSA4nml0c
https://open.spotify.com/track/0mRLrK69GVTA89SqkPw81j
https://open.spotify.com/track/1zAmcM2z58kvlMkca8E7RJ
https://open.spotify.com/track/1eHvsswoES1tAnOJKgFP7S
https://open.spotify.com/track/5Rjmo2er50dnk8jUaGnwRh
https://open.spotify.com/track/5RWMlD6s4i8fECJkDvdt7D
https://open.spotify.com/track/3qvI8dcZHrFFHBOxkUjGjj
https://open.spotify.com/track/0VKD63DmYa2ScIFTWGC6GE
https://open.spotify.com/track/72AfoN1wsvI9aGsR1WaRxD
https://open.spotify.com/track/6KOcDX1UMQx49ZSnrRY147
https://open.spotify.com/track/4qCuoewqKE7bAJU8VD9uWQ
https://open.spotify.com/track/4roeKO7cR7DHYudYC8lB8s
https://open.spotify.com/track/4MUQHVgkjLYYqFzzvLRMPI
https://open.spotify.com/track/5LKOgTKEz3Ky9hMoFciDYK
https://open.spotify.com/track/2qaZ43v7NCdrJFtZVSNHYJ
https://open.spotify.com/track/2kbbvppgJd9rz18gfsofRI
https://open.spotify.com/track/4waAOpgaQ6WvWLir6xowWD
https://open.spotify.com/track/7p31Q6SFArvZ0LWCnGPUTp
https://open.spotify.com/track/7BpVF4TqZ2Pp60r6WJ3cDD
https://open.spotify.com/track/4DmjHCRymTq4eCUkWAUGZs
https://open.spotify.com/track/79nopNMm3rJSUfQfMkG0Ur
https://open.spotify.com/track/44tDqFYrK86OVaxzLhUbVv
https://open.spotify.com/track/0ObzbR5uD7GKZzxhQ28DX9
https://open.spotify.com/track/44mJCZNSG0dLPKHXDekPLC
https://open.spotify.com/track/6C7ssw6k1yVwZS4PNMD849
https://open.spotify.com/track/3pghcSNOm4yKqWEghNCIL6
https://open.spotify.com/track/3ps2drTqdO29miQG4s6kMV
https://open.spotify.com/track/13LqCh4tcyxDJbALYVCN8b
https://open.spotify.com/track/30sg5TXrB6tgCdvzpvhRg1
https://open.spotify.com/track/4CanBdgpe7SkperJ9SUoLN
https://open.spotify.com/track/0KLGSEjKAwzTF7wtjKmXdj
https://open.spotify.com/track/4NZ49ou8OhP8AIlmFmmNUu
https://open.spotify.com/track/3D7HuUWt0aGZHg7r2Ra3vv
https://open.spotify.com/track/4rkh7pbmA7OhuSEzn8DBAk
https://open.spotify.com/track/00ixI0fRvOuSybl5ltQS4T
https://open.spotify.com/track/2OjpUNTChdSo88YXByzkoZ
https://open.spotify.com/track/7aIIEZi4zigYSc5W4xP2tX
https://open.spotify.com/track/1TdHsiNlpUzK9mtG36cDIq
https://open.spotify.com/track/3xiCMjkPfM0gx3iic21tu8
https://open.spotify.com/track/4pSXw0z80rx6OvDvUMkdqH
https://open.spotify.com/track/5fZCHpce0NM7YCXdrZKnFZ
https://open.spotify.com/track/5QyQ6um0y3LPjnCiN73Pgo
https://open.spotify.com/track/3dP1f7L5XyiX5q2LB8Ywsg
https://open.spotify.com/track/1SQMYA7cISWamKpRP3UqnH
https://open.spotify.com/track/5UKaekWw9wkPts9iORegbP
https://open.spotify.com/track/1SniLCGHvhHJAiGe3hLboq
https://open.spotify.com/track/26sqM0uf0Ka7FDHkdRfbqh
https://open.spotify.com/track/4Nj7jHfEE44ppiAmVex7yJ
https://open.spotify.com/track/7qxXRr0ksnXmY0eXry1rjo
https://open.spotify.com/track/16tcBvtiZHoWfjzTyL4wOx
https://open.spotify.com/track/1i8MaQec4fQXj1enX8ZWF4
https://open.spotify.com/track/2DMrZd3PtIxGJH5qCr3exu
https://open.spotify.com/track/3lQyUzsLQRQuYf52OL5X1L
https://open.spotify.com/track/4905lIuy3D3OHnMMkQ9Zf3
https://open.spotify.com/track/5IfrPQNOYlUfFh2vJfEiTt
https://open.spotify.com/track/4Y20Xr3etfpa3Nz8mjXg24
https://open.spotify.com/track/6uQaPMGHgvqYG3Jb2VlKqG
https://open.spotify.com/track/5Iu5WSN1HcB0x8NGxBfARp
https://open.spotify.com/track/1gKnk5AoVjaKOGGp4f48CX
https://open.spotify.com/track/5iTMWX6wdY27SoiEK0mylf
https://open.spotify.com/track/6kJT6sSdArQFVhccIFZJY3
https://open.spotify.com/track/2UZXQrEi0A8ySbDuyeuMt3
https://open.spotify.com/track/6EMoFOfRE7gudmtp4m5CXJ
https://open.spotify.com/track/6pfvfeG3cm82Pu6GdXybF7
https://open.spotify.com/track/64swGjmvYUUJyJU1XDotVn
https://open.spotify.com/track/4YaxzkqLGqa5YK1yXNvMdb
https://open.spotify.com/track/5Oc4FV7Xst4FV6h7Djnhza
https://open.spotify.com/track/3JAYfsoBmU7VNFA1fDth8i
https://open.spotify.com/track/7AcP7i4UYSxAVrWkotR8o5
https://open.spotify.com/track/4OoA6TCEGhRPdCqn5V2Wci
https://open.spotify.com/track/6XJkFdxn0DLA17lTv54pKS
https://open.spotify.com/track/0IFRyG6MmS9a1VrN14EdEa
https://open.spotify.com/track/37UdefUemIsHeYjQjGfhJY
https://open.spotify.com/track/3ZUaWQ4El60fwxqPIN5B1N
https://open.spotify.com/track/4q3hUmp4DIaZj8yU7R73SJ
https://open.spotify.com/track/63DgAIEz5zxjPDsqPQDp1l
https://open.spotify.com/track/7kl4iIkovU0P5rxxjmdWjx
https://open.spotify.com/track/2SakmEe1WzH0s3fTv30Mx1
https://open.spotify.com/track/4008dpmqe3536dNu10BVAM
https://open.spotify.com/track/66681mGEKwFXG6oSDhAUQv
https://open.spotify.com/track/0EiAcdiAaKFzL57f2QGzhX
https://open.spotify.com/track/4ROCTb4qavWSS4hNqi3t3M
https://open.spotify.com/track/43NjUNxgL9UaM6PEysFui8
https://open.spotify.com/track/3hGue2aulR4wFnkdaYI4I9
https://open.spotify.com/track/4qlOxxnkrmaQuKdtMpCoek
https://open.spotify.com/track/5MChi9fdCbTIWDJPPUuuW6
https://open.spotify.com/track/4vELW2hK9eb9R7Q0BNJ3Vh
https://open.spotify.com/track/5EUPK2x1wJ5bERxjQ47lKv
https://open.spotify.com/track/2xwsawgNmeP5WRuOlyip0g
https://open.spotify.com/track/0cnf0dpmit0mpNXVsJWq7Z
https://open.spotify.com/track/6GccSdxNtnObEYqB5W3dZA
https://open.spotify.com/track/62wk9WOLxInVcRgDN3DNye
https://open.spotify.com/track/1aQoSg3ZSS9NumpQfp4Fai
https://open.spotify.com/track/2FDbtVfQbKlrYLg57ttPQv
https://open.spotify.com/track/3RtuzesB8hFETgOwj8ojzP
https://open.spotify.com/track/7p5WNhM1wIyzW82a9KPfRe
https://open.spotify.com/track/4gqlmugjREVQO8ToGIcQL4
https://open.spotify.com/track/7LNxmFW0IulF94zuQ23Nxw
https://open.spotify.com/track/13mxCVFvZhGk55zXo8W879
https://open.spotify.com/track/2IL2jSIAIoka0Shc5bceVj
""".split()

def is_safe(s):
    return s.isalnum() and all(ord(c) < 128 for c in s)

def safestr(s):
    return "".join(x if is_safe(x) else '_' for x in s).lower()

def downloadPreview(url, path):
    r = requests.get(url, stream=True)
    if r.status_code == 200:
        with open(path, 'wb') as f:
            for chunk in r:
                f.write(chunk)

def getInfo(track_id):
    url = 'https://api.spotify.com/v1/tracks/%s'%(track_id,)
    print url
    r = requests.get(url)
    j = r.json()
    return (j['artists'][0]['name'], j['name'], j['preview_url'])

def convertToOgg(mp3, ogg):
    subprocess.call(['ffmpeg', '-i', mp3, '-y', '-codec:a', 'libvorbis', ogg])

def main(download_folder):
    rexp = re.compile(r"https://open.spotify.com/track/(.*)")
    for track in PLAYLIST:
        m = rexp.match(track)
        if m:
            artist, track, url = getInfo(m.group(1))
            if not url:
                continue
            artist_safe = safestr(artist)
            track_safe = safestr(track)
            try:
                os.makedirs(os.path.join(download_folder, artist_safe))
            except OSError:
                pass
            track_path_no_ext = os.path.join(download_folder, artist_safe, track_safe)
            downloadPreview(url, track_path_no_ext + ".mp3")
            convertToOgg(track_path_no_ext + ".mp3", track_path_no_ext + ".ogg")
            os.remove(track_path_no_ext + ".mp3")

if __name__=="__main__":
    # This depends on ffmpeg with libvorbis:
    # brew install ffmpeg --with-libvorbis
    main(sys.argv[1])
