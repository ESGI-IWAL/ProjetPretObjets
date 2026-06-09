<script lang="ts" setup>

import { computed } from 'vue'
import useToaster from '../composables/useToaster'

const { toasts, removeToast } = useToaster()

const list = computed(() => toasts.value)
</script>

<template>
	<div class="toaster-wrapper" aria-live="polite">
		<transition-group name="toast" tag="div">
			<div
				v-for="toast in list"
				:key="toast.id"
				:class="['toast', toast.type]
				"
			>
				<div class="toast-content">
					<strong class="toast-title">{{ toast.type === 'success' ? 'OK' : 'Erreur' }}</strong>
					<div class="toast-message">{{ toast.message }}</div>
				</div>
				<button class="toast-close" @click="removeToast(toast.id)">✕</button>
			</div>
		</transition-group>
	</div>
</template>


<style scoped>
.toaster-wrapper {
	position: fixed;
	left: 50%;
	transform: translateX(-50%);
	top: 16px;
	bottom: auto;
	z-index: 1000;
	display: flex;
	flex-direction: column;
	gap: 8px;
	align-items: center;
	pointer-events: none; /* let clicks pass through except on buttons */
	width: auto;
	max-width: calc(100% - 32px);
}
.toast-enter-active, .toast-leave-active {
	transition: all 200ms ease;
}
.toast-enter-from {
	opacity: 0;
	transform: translateY(-8px) scale(0.98);
}
.toast-leave-to {
	opacity: 0;
	transform: translateY(-8px) scale(0.98);
}
.toast {
	min-width: 220px;
	max-width: 720px;
	width: 100%;
	padding: 10px 14px;
	border-radius: var(--border-radius, 12px);
	box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
	display: flex;
	align-items: center;
	justify-content: space-between;
	gap: 8px;
	pointer-events: auto;
	font-family: var(--font-family-sans);
}
.toast.success {
	background-color: #dff0de;
	border: 1px solid #629460;
	color: #305430;
}
.toast.error {
	background-color: #f5e6df;
	border: 1px solid #c47a5a;
	color: #7a3a1e;
}
.toast-content {
	display: flex;
	flex-direction: column;
}
.toast-title {
	font-weight: 600;
	font-size: 0.9rem;
}
.toast-message {
	font-size: 0.85rem;
}
.toast-close {
	background: transparent;
	border: none;
	cursor: pointer;
	font-size: 0.9rem;
	color: inherit;
}
</style>