interface IUsePretDetaille {
    detailLoan : Ref<boolean>,
    toggleDetailLoan : () => void
}

export const useDetailLoan = () : IUsePretDetaille => {
    const detailLoan = ref<boolean>(false)

    const toggleDetailLoan = () => {
        detailLoan.value = !detailLoan.value
    }

    return {
        detailLoan,
         toggleDetailLoan
    }
} 