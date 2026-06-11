
export type ToastType = 'success' | 'error'

export interface Toast {
  id: number
  message: string
  type: ToastType
  timeout?: number
}

export function useToaster() {
  const toasts = useState<Toast[]>('toasts', () => [])

  const add = (message: string, type: ToastType = 'success', timeout = 3500) => {
    const id = Date.now() + Math.floor(Math.random() * 1000)
    const toast: Toast = { id, message, type, timeout }
    toasts.value.push(toast)

    if (timeout && timeout > 0) {
      setTimeout(() => {
        remove(id)
      }, timeout)
    }
    return id
  }

  const remove = (id: number) => {
    const idx = toasts.value.findIndex(t => t.id === id)
    if (idx !== -1) toasts.value.splice(idx, 1)
  }

  const clear = () => {
    toasts.value = []
  }

  return {
    toasts,
    show: add,
    removeToast: remove,
    clear,
  }
}

export default useToaster
